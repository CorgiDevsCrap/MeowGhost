package dev.corgi.autosave;

import java.io.*;
import java.util.ArrayList;

import dev.corgi.MeowGhost;
import dev.corgi.friends.FriendsManager;
import net.minecraft.client.Minecraft;

public class FriendsSaveLoad {

    private File dir;
    private File friendsFile;
    public String iHateMyLife;

    public FriendsSaveLoad() {
        dir = new File(Minecraft.getMinecraft().mcDataDir, "meowghost");
        if (!dir.exists()) {
            dir.mkdir();
        }
        friendsFile = new File(dir, "friends.txt");
        if (!friendsFile.exists()) {
            try {
                friendsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.loadFriends();
    }

    public void saveFriends(ArrayList<String> friends) {
        if (MeowGhost.instance.destructed) {
            return;
        }

        ArrayList<String> toSave = new ArrayList<>();

        for (String friend : friends) {
            toSave.add("FRIEND:" + friend);
        }

        try (PrintWriter pw = new PrintWriter(this.friendsFile)) {
            for (String str : toSave) {
                pw.println(str);
            }
            System.out.println("Friends saved successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void loadFriends() {
        if (MeowGhost.instance.destructed) {
            return;
        }

        ArrayList<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.friendsFile));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String s : lines) {
            String[] args = s.split(":");
            if (s.toLowerCase().startsWith("mod:")) {
                // Handle module loading
            } else if (s.toLowerCase().startsWith("set:")) {
                // Handle setting loading
            } else if (s.toLowerCase().startsWith("friend:")) {

                // Handle friend loading
                String friendName = args[1];
                FriendsManager friendsManager = MeowGhost.instance.friendsManager;  // Assuming you have a FriendsManager
                if (friendsManager.checkFriend(friendName)) {
                    // Friend exists, perform necessary actions
                } else {
                    // Friend doesn't exist, you can add it to the friends list if needed
                    friendsManager.addFriends(friendName);
                }
            }
        }
    }

}
