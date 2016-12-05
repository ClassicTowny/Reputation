package me.grossen.reputation;

import java.util.*;

public class PlayerData {

    private Reputation plugin;

    private UUID uuid;
    private int goodReputation;
    private int badReputation;
    private Map<String, Character> receivedFrom = new HashMap<>();

    public PlayerData(Reputation plugin, UUID uuid) {
        this.plugin = plugin;

        this.uuid = uuid;
        if (plugin.getDataLoader().contains(String.valueOf(uuid) + ".goodRep")) {
            goodReputation = plugin.getDataLoader().getInt(String.valueOf(uuid) + ".goodRep");
        } else {
            plugin.getDataLoader().set(String.valueOf(uuid) + ".goodRep", 0);
        }
        if (plugin.getDataLoader().contains(String.valueOf(uuid) + ".badRep")) {
            badReputation = plugin.getDataLoader().getInt(String.valueOf(uuid) + ".badRep");
        } else {
            plugin.getDataLoader().set(String.valueOf(uuid) + ".badRep", 0);
        }
        if (plugin.getDataLoader().contains(String.valueOf(uuid) + ".received")) {
            for (String s : plugin.getDataLoader().getStringList(String.valueOf(uuid) + ".received")) {
                String[] split = s.split(":");
                receivedFrom.put(split[0], s.charAt(split[0].length() + 1));
            }
        }
        plugin.saveDataFile();
    }

    public int getGoodReputation() {
        return goodReputation;
    }

    public int getBadReputation() {
        return badReputation;
    }

    public boolean hasReceivedFrom(UUID uuid) {
        return receivedFrom.containsKey(String.valueOf(uuid));
    }

    public char getReceivedFromType(UUID uuid) {
        return receivedFrom.get(String.valueOf(uuid));
    }

    public void addReputation(UUID giver, char type) {
        if (!receivedFrom.containsKey(String.valueOf(giver))) {
            if (type == '+') {
                goodReputation++;
            } else {
                badReputation++;
            }
            receivedFrom.put(String.valueOf(giver), type);
        } else {
            if (receivedFrom.get(String.valueOf(giver)) == '+') {
                goodReputation--;
            } else {
                badReputation--;
            }
            if (type == '+') {
                goodReputation++;
            } else {
                badReputation++;
            }
            receivedFrom.remove(String.valueOf(giver));
            receivedFrom.put(String.valueOf(giver), type);
        }
        plugin.getDataLoader().set(String.valueOf(uuid) + ".goodRep", goodReputation);
        plugin.getDataLoader().set(String.valueOf(uuid) + ".badRep", badReputation);
        List<String> tempList = new ArrayList<>();
        receivedFrom.keySet().forEach(s -> tempList.add(s + ":" + receivedFrom.get(s)));
        plugin.getDataLoader().set(String.valueOf(uuid) + ".received", tempList);
        plugin.saveDataFile();
    }
}
