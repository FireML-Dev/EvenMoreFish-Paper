package com.oheers.fish.competition.strategies;


import com.oheers.fish.EvenMoreFish;
import com.oheers.fish.config.messages.PaperMessage;
import com.oheers.fish.competition.Competition;
import com.oheers.fish.competition.CompetitionEntry;
import com.oheers.fish.competition.CompetitionStrategy;
import com.oheers.fish.competition.CompetitionType;
import com.oheers.fish.competition.leaderboard.Leaderboard;
import com.oheers.fish.config.messages.ConfigMessage;
import com.oheers.fish.fishing.items.Fish;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomStrategy implements CompetitionStrategy {
    private CompetitionType randomType;
    @Override
    public boolean begin(Competition competition) {
        competition.setCompetitionType(getRandomType());
        this.randomType = competition.getCompetitionType();
        competition.setOriginallyRandom(true);
        return true;
    }

    @Override
    public void applyToLeaderboard(Fish fish, Player fisher, Leaderboard leaderboard, Competition competition) {
        randomType.getStrategy().applyToLeaderboard(fish,fisher,leaderboard,competition);
    }

    @Override
    public PaperMessage getSingleConsoleLeaderboardMessage(@NotNull PaperMessage message, @NotNull CompetitionEntry entry) {
        return randomType.getStrategy().getSingleConsoleLeaderboardMessage(message, entry);
    }

    @Override
    public PaperMessage getBeginMessage(Competition competition, CompetitionType type) {
        return randomType.getStrategy().getBeginMessage(competition, type);
    }

    @Override
    public PaperMessage getSinglePlayerLeaderboard(@NotNull PaperMessage message, @NotNull CompetitionEntry entry) {
        return randomType.getStrategy().getSinglePlayerLeaderboard(message, entry);
    }

    @Override
    public @NotNull PaperMessage getTypeFormat(@NotNull Competition competition, ConfigMessage configMessage) {
        return randomType.getStrategy().getTypeFormat(competition, configMessage);
    }

    public CompetitionType getRandomType() {
        // -1 from the length so that the RANDOM isn't chosen as the random value.
        int type = EvenMoreFish.getInstance().getRandom().nextInt(CompetitionType.values().length - 1);
        return CompetitionType.values()[type];
    }
}
