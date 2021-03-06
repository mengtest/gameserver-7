package com.game.module.friend;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.data.Response;
import com.game.event.Dispose;
import com.game.event.ILogin;
import com.game.event.InitHandler;
import com.game.module.chat.ChatExtension;
import com.game.module.chat.ChatService;
import com.game.module.daily.DailyService;
import com.game.module.gang.GangService;
import com.game.module.player.Player;
import com.game.module.player.PlayerDao;
import com.game.module.player.PlayerData;
import com.game.module.player.PlayerService;
import com.game.module.scene.SceneService;
import com.game.module.serial.SerialData;
import com.game.module.serial.SerialDataService;
import com.game.params.Int2Param;
import com.game.params.ListParam;
import com.game.params.chat.ChatVo;
import com.game.params.friend.FriendInfo;
import com.game.params.friend.FriendVo;
import com.game.util.ConfigData;
import com.server.SessionManager;

@Service
public class FriendService implements InitHandler, ILogin, Dispose {

	@Autowired
	private SerialDataService serialDataService;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private DailyService dailyService;
	@Autowired
	private SceneService sceneService;
	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private ChatService chatService;
	@Autowired
	private GangService gangService;
	
	private static final int MAX_REQUEST = 10;

	@Override
	public void handleInit() {
		// TODO Auto-generated method stub
		
	}
	
	// 发送请求
	public int addRequest(int playerId, List<Integer> friendIds) {
		// 获取请求队列
		boolean one = friendIds.size() == 1;
		int max = ConfigData.globalParam().maxFriend;
		PlayerData data = playerService.getPlayerData(playerId);
		int respCode = Response.SUCCESS;
		// 加入我的自己的已发送信息
		ConcurrentHashMap<Integer, Boolean> myRequests = serialDataService.getData().getFriendSendRequests()
						.get(playerId);
		if(myRequests == null){
			myRequests = new ConcurrentHashMap<Integer, Boolean>();
			serialDataService.getData().getFriendSendRequests().put(playerId,myRequests);
		}
		for (int id : friendIds) {
			if(id == playerId){
				respCode = Response.NO_ADD_YOURSELF;
				break;
			}
			ConcurrentHashMap<Integer, Boolean> requests = serialDataService.getData().getFriendRequests().get(id);
			if (requests == null) {
				requests = new ConcurrentHashMap<Integer, Boolean>();
				serialDataService.getData().getFriendRequests().put(id, requests);
			}
			if(requests.containsKey(id)){
				continue;
			}
			
			if(data.getFriends().containsKey(id)){
				respCode = Response.IS_FRIEND;
				break;
			}
			
			PlayerData friendData = playerService.getPlayerData(id);
			
			if(friendData.getBlack().containsKey(playerId)){
				continue;
			}
			
			// 如果超过数量,删除一些
			if (requests.size() >= MAX_REQUEST) {
				List<Integer> dels = new ArrayList<Integer>(requests.size());
				dels.addAll(requests.keySet());
				Collections.shuffle(dels);
				requests.remove(dels.get(0));
			}
			int myCount = data.getFriends().size();
			if (myCount >= max) {
				if (one) {
					respCode = Response.MY_MAX_FRIENDS;
				}
				break;
			}
			// 验证对方的好友是否满了
			int hisCount = friendData.getFriends().size();
			if (hisCount >= max) {
				if (one) {
					respCode = Response.HE_MAX_FRIENDS;
				}
				continue;
			}

			// 加入队列
			requests.put(playerId, true);
			serialDataService.getData().getFriendRequests().put(id, requests);
			// 通知对方
			SessionManager.getInstance().sendMsg(FriendExtension.BE_REQUESTED, getVo(playerId), id);
						
			myRequests.put(id, true);

		}
		return respCode;
	}

	// 删除
	public void del(int playerId, Integer friend) {
		PlayerData playerData = playerService.getPlayerData(playerId);
		playerData.getFriends().remove(friend);
		/*PlayerData playerData2 = playerService.getPlayerData(friend);
		if(playerData2 != null){
			playerData2.getFriends().remove(playerId);
		}*/
	}

	// 同意
	public Int2Param approve(int playerId, List<Integer> friendIds) {
		Int2Param result = new Int2Param();
		boolean one = friendIds.size() == 1;
		// 加好友逻辑
		int max = ConfigData.globalParam().maxFriend;
		PlayerData data = playerService.getPlayerData(playerId);

		for (int id : friendIds) {
			// 验证自己的好友是否满了
			int myCount = data.getFriends().size();
			if (myCount >= max) {
				if (one) {
					result.param1 = Response.MY_MAX_FRIENDS;
				}
				break;
			}
			// 验证对方的好友是否满了
			int hisCount = playerService.getPlayerData(id).getFriends().size();
			if (hisCount >= max) {
				if (one) {
					result.param1 = Response.HE_MAX_FRIENDS;
				}
				continue;
			}
			// 清除请求
			serialDataService.getData().getFriendRequests().get(playerId).remove(id);
			if(!data.getFriends().containsKey(id)){
				// 加入好友(双方)
				data.getFriends().put(id, true);
				// 移除黑名单
				Integer black = id;
				data.getBlack().remove(black);
			}
			
			// 对方
			PlayerData friend = playerService.getPlayerData(id);
			if(!friend.getFriends().containsKey(playerId)){				
				friend.getFriends().put(playerId, true);
				friend.getBlack().remove(playerId);
				playerService.updatePlayerData(id);
				// 通知对方
				SessionManager.getInstance().sendMsg(FriendExtension.AGREE_REQUEST, getVo(playerId), id);
			}
			

			result.param2++;
			// 更新对方的请求列表
			if (serialDataService.getData().getFriendSendRequests().get(id) != null) {
				serialDataService.getData().getFriendSendRequests().get(id).remove(playerId);
			}
			
		}
		return result;
	}

	// 拒绝
	public void refuse(int playerId, List<Integer> friendIds) {
		// 移除请求列表
		ConcurrentHashMap<Integer, Boolean> requests = serialDataService.getData().getFriendRequests().get(playerId);
		if (requests == null) {
			return;
		}
		for (int id : friendIds) {
			requests.remove(id);
			if (serialDataService.getData().getFriendSendRequests().get(id) != null) {
				serialDataService.getData().getFriendSendRequests().get(id).remove(playerId);
			}
		}
	}

	// 查找
	public ListParam<FriendVo> find(int playerId, String name) {
		ListParam<FriendVo> result = new ListParam<FriendVo>();
		result.params = new ArrayList<FriendVo>();
		// 名字
		int friendId = playerService.getPlayerIdByName(name);
		if (friendId != 0) {
			result.params.add(getVo(friendId));
		}
		if(name.matches("^\\d+$")){
			friendId = Integer.parseInt(name);
			FriendVo vo = getVo(friendId);
			if(vo != null){
				result.params.add(vo);
			}
		}
		
		return result;
	}

	// 转vo
	public FriendVo getVo(int playerId) {
		Player player = playerService.getPlayer(playerId);
		if(player == null){
			return null;
		}
		FriendVo vo = new FriendVo();
		vo.playerId = player.getPlayerId();
		vo.name = player.getName();
		vo.lev = player.getLev();
		vo.vip = player.getVip();
		vo.online = SessionManager.getInstance().getChannel(playerId) != null;
		vo.fightStrength = player.getFight();
		vo.vocation = player.getVocation();
		if(player.getGangId() > 0){			
			vo.gang = gangService.getGang(player.getGangId()).getName();
		}
		return vo;
	}

	// 获取好友信息
	public FriendInfo getFriendInfo(int playerId) {

		PlayerData data = playerService.getPlayerData(playerId);

		FriendInfo info = new FriendInfo();
		info.friends = new ArrayList<FriendVo>(data.getFriends().size());
		info.blacks = new ArrayList<FriendVo>(data.getBlack().size());
		info.recent = new ArrayList<FriendVo>(data.getRecentContacters().size());
		info.requests = new ArrayList<FriendVo>();

		SerialData serialData = serialDataService.getData();

		// 好友
		for (int id : data.getFriends().keySet()) {
			FriendVo vo = getVo(id);
			info.friends.add(vo);
		}
		
		// 最近联系人
		for (int id : data.getRecentContacters().keySet()) {
			FriendVo vo = getVo(id);
			info.recent.add(vo);
		}

		// 黑名单
		for (int id : data.getBlack().keySet()) {
			FriendVo vo = getVo(id);
			info.blacks.add(vo);
		}
		// 请求
		Map<Integer, Boolean> requests = serialData.getFriendRequests().get(playerId);
		if (requests != null) {
			for (int id : requests.keySet()) {
				if (data.getFriends().containsKey(id)) {
					requests.remove(id);
					continue;
				}
				FriendVo vo = getVo(id);
				info.requests.add(vo);
			}
		}
		
		List<ChatVo> list = chatService.getOffChat(playerId);
		if(list != null && !list.isEmpty()){
			ListParam<ChatVo> result = new ListParam<ChatVo>();
			result.params = new ArrayList<ChatVo>();
			result.params.addAll(list);
			SessionManager.getInstance().sendMsg(ChatExtension.CHAT, result, playerId);
			list.clear();
		}


		return info;
	}

	// 获取当前场景的玩家
	public List<FriendVo> getOnlines(int playerId) {
		int totalCount = 4;
		List<FriendVo> friends = new ArrayList<FriendVo>();

		Player player = playerService.getPlayer(playerId);
		String key = sceneService.getGroupKey(player);
		Collection<Channel> channels = SessionManager.getInstance().getGroupChannels(key);

		// 其他的离线好友
		Map<Integer, Boolean> excludes = new HashMap<Integer, Boolean>();
		excludes.put(playerId, true);

		// 查找好友
		PlayerData data = playerService.getPlayerData(playerId);
		ConcurrentHashMap<Integer, Boolean> requests = serialDataService.getData().getFriendSendRequests()
				.get(playerId);
		if (requests == null) {
			requests = new ConcurrentHashMap<Integer, Boolean>();
		}
		excludes.putAll(requests);
		excludes.putAll(data.getFriends());

		// 场景玩家
		int myLev = player.getLev();
		int levDel = ConfigData.globalParam().friendLevDel;
		int count = 0;
		for (Channel channel : channels) {
			int id = SessionManager.getInstance().getPlayerId(channel);
			if (id == 0 || excludes.containsKey(id)) {
				continue;
			}
			int lev = SessionManager.getInstance().getPlayerLev(channel);
			if(Math.abs(lev - myLev) > levDel){
				continue;
			}
			FriendVo vo = getVo(id);
			friends.add(vo);
			excludes.put(id, true);
			if (count++ > totalCount) {
				break;
			}
		}
		// 竞技场
		/*Integer myRank = serialDataService.getData().getPlayerRanks().get(playerId);
		if (myRank != null) {
			for (int i = 0; i < 5; i++) {
				int hisRank = myRank++;

				Integer arenaId = serialDataService.getData().getRanks().get(hisRank);
				if (arenaId == null || arenaId / 1000 <= 5000 || excludes.containsKey(arenaId)) {
					continue;
				}
				friends.add(getVo(arenaId));
				excludes.put(arenaId, true);
			}
		}*/
		
		// 其他的离线
		if (friends.size() < totalCount) {
			List<Integer> other1 = playerDao.selectFriends(new ArrayList<Integer>(excludes.keySet()), myLev + levDel, myLev - levDel, 10);
			for (int id : other1) {
				friends.add(getVo(id));
			}
		}
		
		if (friends.isEmpty()) {
			return friends;
		}
		// 排序
		/*Collections.sort(friends, new Comparator<FriendVo>() {
			@Override
			public int compare(FriendVo o1, FriendVo o2) {
				int delLev = Math.abs(myLev - o2.lev) - Math.abs(myLev - o1.lev);
				
				return delLev;
			}
		
		});*/
		Collections.shuffle(friends);
		int size = Math.min(totalCount, friends.size());
		return friends.subList(0, size);
	}

	@Override
	public void playerLogined(int playerId) {
		stateChange(playerId, 1);
	}
	
	@Override
	public void removeCache(int playerId) {
		// 又重新登录了
		if(SessionManager.getInstance().isActive(playerId)){
			return;
		}
		stateChange(playerId, 0);
	}
	
	private void stateChange(int playerId, int code){
		PlayerData data = playerService.getPlayerData(playerId);

//		FriendInfo info = new FriendInfo();
//		info.friends = new ArrayList<FriendVo>(data.getFriends().size());
//		info.recent = new ArrayList<FriendVo>(data.getRecentContacters().size());

		Int2Param state = new Int2Param();
		state.param1 = playerId;
		state.param2 = code;
		
		Map<Integer, Boolean> friends = data.getFriends();
		// 好友
		for (int id : friends.keySet()) {
			
			SessionManager.getInstance().sendMsg(FriendExtension.STATE_CHANGE, state, id);
		}
		
		// 最近联系人
		for (int id : data.getRecentContacters().keySet()) {
			if(!friends.containsKey(id) && SessionManager.getInstance().isActive(id)){
				PlayerData pd = playerService.getPlayerData(id);
				if(pd.getRecentContacters().containsKey(id)){
					SessionManager.getInstance().sendMsg(FriendExtension.STATE_CHANGE, state, id);
				}
			}
		}
	}

	
	
}
