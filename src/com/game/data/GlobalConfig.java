package com.game.data;
import java.util.Map;
/**
* q全局配置表.xlsx(自动生成，请勿编辑！)
*/
public class GlobalConfig {
	public int id;//Key
	public int maxRoleCount;//创建角色上限
	public int[] bagSize;//初始背包大小
	public float defaultScale;//默认缩放值
	public float[] defaultPos;//人物默认逻辑位置
	public float[] defaultAIPos;//AI竞技场AI对手逻辑位置
	public int[] playerDefaultProperty;//人物初始属性
	public int[][] playerDefaultSkill;//人物初始技能
	public int defaultVocation;//选择人物职业
	public int reducePercentParamA;//减伤比参数A
	public float reducePercentParamB;//减伤比参数B
	public float critFactor;//暴击倍率
	public float critParamA;//暴击几率参数A
	public float critParamB;//暴击几率参数B
	public float resistanceParamA;//抗性减伤参数A
	public float resistanceParamB;//抗性减伤参数B
	public int symptomParam;//症状参数
	public int worldChatLevel;//世界频道聊天开放等级
	public int[] equipTypes;//装备类型
	public int[] specTypes;//特殊物品类型
	public int resetCopyPrice;//重置副本价格
	public int maxFriend;//最大好友数
	public int createGangLev;//创建公会需要等级
	public int createGangDiamond;//创建公会需要钻石
	public int applyGangLev;//加入公会需要等级
	public int quitPunish;//退出惩罚（小时）
	public int applyLimit;//待审批的申请个数限制
	public int[][] donateParams;//捐献参数
	public int person2gang;//个人贡献转公会财产百分比
	public int gangCompensate;//转让会长时补偿的贡献限制
	public int renameGang;//重命名公会消耗
	public int gangBrocast;//公会广播消耗
	public int[] gangTasks;//公会固定任务
	public int[] firstTask;//初始化任务id
	public int version;//版本
	public int maxEnergy;//初始体力
	public int firstScene;//初始场景
	public int[] headId;//初始头部
	public int[] fashionId;//初始衣服
	public int[] weaponId;//初始武器
	public int[][] initEquips;//初始装备
	public int[][] newbieMailReward;//新手奖励
	public int restoreEnergy;//体力恢复
	public int strengthTicket;//强化券id
	public int strengthTicketAdd;//强化券增加概率
	public Map<Integer,int[]> jewelCost;//宝石升级消耗道具类型
	public Map<Integer,int[]> jewelOpenLev;//宝石孔开启等级
	public int clearCostCoin;//洗练单次消耗金币
	public int clearCostDiamond;//洗练单次锁定钻石
	public float[] fightParams;//战力参数
	public int reviveCount;//单次副本复活次数
	public float roleMoveFactor;//主角移动比率
	public int magicClipId;//神奇碎片物品id
	public Map<Integer,int[]> shopRefreshPrice;//商城刷新价格
	public String shopRefreshTime;//商城刷新时间
	public int mysteryShopTime;//神秘商店存在时间
	public int[] mysteryTriggerCopyType;//触发神秘商店副本类型
	public int mysteryTriggerPower;//触发神秘商店体力
	public int[] mysteryProperty;//神秘商店概率
	public int[] mysterySpecialCopyIds;//神秘商店特殊副本ID
	public int comboCount;//连击播放特效
	public float dyingRedFlash;//血量百分比播放闪红
	public int maxVIP;//最大VIP
	public int worldChatInterval;//世界频道聊天冷却
	public int arenaChallenge;//AI竞技场挑战次数
	public Map<Integer,Integer> arenaWinReward;//AI竞技场胜利奖励
	public Map<Integer,Integer> arenaLostReward;//AI竞技场失败奖励
	public int[][] arenaBuyChallenge;//AI竞技场购买次数价格
	public int personChatLev;//陌生人私聊最低等级
	public int friendLevDel;//推荐好友等级差
	public float cameraFixedX;//摄像机X轴固定旋转角度
	public float cameraDistH;//摄像机到人物的水平距离
	public float cameraDistV;//摄像机到人物的垂直距离
	public float cameraFixedYaw;//摄像机Y轴固定旋转角度
	public Map<Integer,Integer> buyTreasurePrice;//精绝宝藏次数购买价格
	public int treasureDelTime;//精绝宝藏挑战时间间隔（秒）
	public Map<Integer,Integer> quickTreasureCopy;//精绝宝藏快速完成价格
	public Map<Integer,Integer> sweepNeedGoods;//扫荡普通副本消耗
	public int crazyChestChallengeCount;//精绝宝藏每日挑战次数
	public float crazyChestLightningIntervalTime;//精绝宝藏闪电冷却时间
	public int[] experienceFightSection;//英雄试炼战力区间
	public float[] exprienceFightRatio;//英雄试炼关卡战力百分比
	public int[] exprienceRewards;//英雄试炼宝箱奖励
	public int sceneFadeDistance;//场景物件透明判断距离
	public int guildBroadcastPrice;//公会广播费用
	public Map<Integer,Integer> buyExtremeEvasionPrice;//极限躲避次数购买价格
	public int extremeEvasionDelTime;//极限躲避挑战时间间隔（秒）
	public Map<Integer,Integer> quickExtremeEvasionCopy;//极限躲避快速完成价格
	public int extremeEvasionChallengeCount;//极限躲避每日挑战次数
	public float extremeEvasionHitCtrlParam;//极限躲避伤害控制参数
	public float crazyChestHitCtrlParam;//精绝宝藏伤害控制参数
	public int crazyChestHitPercent;//精绝宝藏伤害百分比
	public Map<Integer,int[][]> taskLivenessReward;//活跃度奖励
	public int[] restoreTraversingEnergy;//穿越仪能量恢复
	public int[] copyBackgroundIds;//副本背景图资源
	public Map<Integer,Integer> copyBackgroundEffects;//副本背景特效
	public Map<Integer,Integer> noCurrencyTips;//货币不足提示语
	public int[][] worldBossReward;//世界Boss排名奖励
	public int[][] worldBossRewardRank;//世界Boss排名区间
	public Map<Integer,int[]> worldBossLastFightReward;//世界Boss最后一击奖励
	public Map<Integer,int[]> worldBossKillReward;//世界Boss击杀奖励
	public int[] worldBossCost;//世界Boss购买消耗
	public int[] worldBossBuyAttackCost;//世界Boss攻击力购买消耗
	public int[] worldBossCopy;//世界Boss副本ID-BOSSID
	public int[] worldBossHurtReward;//世界Boss伤害兑换奖励
	public int[] worldBossOpenTime;//世界Boss活动时间
	public Map<Integer,Integer> teamLimit;//队伍人数上限
	public int[] skillCardGroupOpenVip;//技能卡卡组开启的VIP等级
	public int[][] rewardRate;//抽奖界面预览出现率
	public Map<Integer,float[][]> mainCityPlayerInfo;//主城角色的位置,朝向,大小
	public float[] mainCityCameraInfo;//主城相机朝向(上下左右)
	public float[] attackMinThreshold;//远程技能最小攻击范围
	public float[] attackSafeThreshold;//远程技能安全攻击范围
	public float fameAddRate;//声望代表加成百分比
	public float MultiHitMaxInterval;//连击最大间隔时长
	public float BeHitMaxInterval;//受击叠加伤害间隔时长
	public int[] groupCopyOpenTime;//团队副本活动时间
	public int[] groupRewardRate;//团队副本奖励加成
	public int groupTimes;//团队副本活动次数
	public int LeadawayCopyDelTime;//顺手牵羊挑战时间间隔（秒）
	public Map<Integer,Integer> quickLeadawayCopy;//顺手牵羊快速完成价格
	public Map<Integer,Integer> buyLeadawayPrice;//顺手牵羊次数购买价格
	public int LeadawayChallengeCount;//顺手牵羊每日挑战次数
	public float[] LeadawayGoldAppearTime;//顺手牵羊出现金币频率
	public int LeadawayGoldCount;//顺手牵羊金币个数
	public float[] LeadawayPlayerPosition;//顺手牵羊角色位置
	public float[] LeadawayPlayerScale;//顺手牵羊角色缩放
	public float[] LeadawayPlayerRotate;//顺手牵羊角色旋转
	public Map<Integer,int[][]> CatchGoldRocketMapping;//接金币金币与导弹效果对应表
	public int CatchGoldDelTime;//接金币挑战时间间隔（秒）
	public Map<Integer,Integer> quickCatchGoldCopy;//接金币快速完成价格
	public Map<Integer,Integer> catchGoldPrice;//接金币次数购买价格
	public int catchGoldChallengeCount;//接金币每日挑战次数
	public int PkProtect;//段位保护
	public float PkAdd;//额外增加或减少的积分比
	public float PkDec;//额外减少的积分比
	public int PKBattleTime;//战斗时间
	public int PKRecordSize;//战报缓存数量
	public int PKTimeLimit;//赛季时间类型
	public int[] PKTime;//开放时间段
	public String PKBeginDate;//开放日期
	public String PKEndDate;//结束日期
	public int[] robotFight;//机器人战斗力
	public int[] QualifyingRankReward;//排位赛段位奖励
	public int QualifyingMatchingPredictTime;//排位赛匹配预计时间(秒)
	public float joyActionTimeMini;//休闲动作间隔最小时间
	public float joyActionTimeMax;//休闲动作间隔最大时间
	public int guildCopyTimes;//公会副本每日次数
	public int guildRewardRate;//公会副本伤害奖励系数
	public int[] energyPrice;//购买体力价格
	public int addEnergyPerTime;//每次购买增加体力
	public int[] coinPrice;//金币购买价格
	public int coinBuy;//金币购买获得金币数
	public Map<Integer,Integer> buffStrikenEffectPriority;//buff受击特效优先级
	public int[][] cardLvUpPrice;//技能卡合成价格
}