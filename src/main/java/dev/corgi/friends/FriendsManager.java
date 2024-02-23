package dev.corgi.friends;

import dev.corgi.MeowGhost;

import java.util.ArrayList;

public class FriendsManager {

    public ArrayList<String> friends = new ArrayList<>();

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void addFriends(String friend) {
        friends.add(friend);
    }

    public void removeFriends(String friend) {
        if (friends != null && friends.contains(friend)) {
            friends.remove(friend);
        }
    }

    public boolean checkFriend(String input) {
        if(friends.contains(input) && friends != null) {
            return true;
        }
        return false;
    }



}
