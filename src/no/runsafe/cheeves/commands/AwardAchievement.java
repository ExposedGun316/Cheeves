package no.runsafe.cheeves.commands;

import no.runsafe.cheeves.Achievement;
import no.runsafe.cheeves.AchievementFinder;
import no.runsafe.cheeves.AchievementHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafeAmbiguousPlayer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class AwardAchievement extends ExecutableCommand
{
	public AwardAchievement(AchievementHandler achievementHandler, AchievementFinder achievementFinder)
	{
		super("awardach", "Awards an achievement to a player", "runsafe.cheeves.award", "player", "achievementID");
		this.achievementHandler = achievementHandler;
		this.achievementFinder = achievementFinder;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		RunsafePlayer player = RunsafeServer.Instance.getPlayer(parameters.get("player"));

		if (player != null)
		{
			if (player instanceof RunsafeAmbiguousPlayer)
				return player.toString();

			Achievement achievement = this.achievementFinder.getAchievementByID(Integer.valueOf(parameters.get("achievementID")));
			if (achievement == null)
				return "&cNo achievement with that ID.";

			this.achievementHandler.awardAchievement(achievement, player);
			return null;
		}
		return "&cUnable to find player";
	}

	private AchievementHandler achievementHandler;
	private AchievementFinder achievementFinder;
}
