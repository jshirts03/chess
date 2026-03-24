package client.responses;

import client.GameData;

import java.util.ArrayList;

public record ListGamesResponse(
        ArrayList<GameData> games, String message) {
}
