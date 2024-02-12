package dev.corgi.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MouseManager {
  private static final List<Long> leftClicks = new ArrayList<>();
  
  private static final List<Long> rightClicks = new ArrayList<>();
  
  public static long leftClickTimer = 0L;
  
  public static long rightClickTimer = 0L;
  
  @SubscribeEvent
  public void onMouseUpdate(MouseEvent mouse) {
    if (mouse.buttonstate)
      if (mouse.button == 0) {
        addLeftClick();
      } else if (mouse.button == 1) {
        addRightClick();
      }  
  }
  
  public static void addLeftClick() {
    leftClicks.add(Long.valueOf(leftClickTimer = System.currentTimeMillis()));
  }
  
  public static void addRightClick() {
    rightClicks.add(Long.valueOf(rightClickTimer = System.currentTimeMillis()));
  }
  
  public static int getLeftClickCounter() {
    if (!PlayerUtil.isPlayerInGame())
      return leftClicks.size(); 
    for (Long lon : leftClicks) {
      if (lon.longValue() < System.currentTimeMillis() - 1000L) {
        leftClicks.remove(lon);
        break;
      } 
    } 
    return leftClicks.size();
  }
  
  public static int getRightClickCounter() {
    if (!PlayerUtil.isPlayerInGame())
      return leftClicks.size(); 
    for (Long lon : rightClicks) {
      if (lon.longValue() < System.currentTimeMillis() - 1000L) {
        rightClicks.remove(lon);
        break;
      } 
    } 
    return rightClicks.size();
  }
}
