package service.responses;

import datatypes.GameData;

import java.util.HashSet;

public record ListGamesResponse(HashSet<GameData> games) {
}
