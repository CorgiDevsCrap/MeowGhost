package dev.corgi.friends;

import dev.corgi.MeowGhost;
import dev.corgi.alt.auth.LegacySessionChanger;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiAddFriend extends GuiScreen {
    private GuiTextField username;

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            if(this.username.getText().equals("")) {
                this.mc.displayGuiScreen(new GuiAddFriend());
            } else {
                MeowGhost.instance.friendsManager.addFriends(this.username.getText());
                MeowGhost.instance.friendsSaveLoad.saveFriends(MeowGhost.instance.friendsManager.friends);
            }
        }
        if(button.id == 1) {
            if(this.username.getText().equals("")) {
                this.mc.displayGuiScreen(new GuiAddFriend());
            } else {
                MeowGhost.instance.friendsManager.removeFriends(this.username.getText());
                MeowGhost.instance.friendsSaveLoad.saveFriends(MeowGhost.instance.friendsManager.friends);
            }
        }
    }

    @Override
    public void drawScreen(final int x2, final int y2, final float z2) {
        drawDefaultBackground();
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.username.drawTextBox();
        Gui gui = new Gui();
		gui.drawCenteredString(this.mc.fontRendererObj, MeowGhost.instance.friendsManager.getFriends().toString(), (int)(this.width / 2), (int)(sr.getScaledHeight() / 2 - 65), -1);
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 10, this.height / 2 - 20, 120, 20, I18n.format("Add Friend", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50 - 10, this.height / 2 + 10, 120, 20, I18n.format("Remove Friend", new Object[0])));
        (this.username = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 50 - 10, sr.getScaledHeight() / 2 - 50, 120, 20)).setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }


    @Override
    protected void keyTyped(final char character, final int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t' && !this.username.isFocused()) {
            this.username.setFocused(true);
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(final int x2, final int y2, final int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.loadEntityShader(null);
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
    }
}