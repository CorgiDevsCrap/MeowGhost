package dev.corgi.utils;

public class Timer {
  public static long lastMS = System.currentTimeMillis();
  
  private final float updates;
  
  private float cached;
  
  private long last;
  
  public Timer(float updates) {
    this.updates = updates;
  }
  
  public float getValueFloat(float b, float e, int m) {
    if (this.cached == e)
      return this.cached; 
    float t = (float)(System.currentTimeMillis() - this.last) / this.updates;
    switch (m) {
      case 1:
        t = (t < 0.5F) ? (4.0F * t * t * t) : ((t - 1.0F) * (2.0F * t - 2.0F) * (2.0F * t - 2.0F) + 1.0F);
        break;
      case 2:
        t = (float)(1.0D - Math.pow((1.0F - t), 5.0D));
        break;
      case 3:
        t = bounce(t);
        break;
    } 
    float val = b + t * (e - b);
    if (e < val)
      val = e; 
    if (val == e)
      this.cached = val; 
    return val;
  }
  
  public int getValueInt(int b, int e, int t) {
    return Math.round(getValueFloat(b, e, t));
  }
  
  public void start() {
    this.cached = 0.0F;
    this.last = System.currentTimeMillis();
  }
  
  private float bounce(float t) {
    double l = 7.5625D;
    double j = 2.75D;
    if (t < 1.0D / j)
      return (float)(l * t * t); 
    if (t < 2.0D / j)
      return (float)(l * (t = (float)(t - 1.5D / j)) * t + 0.75D); 
    if (t < 2.5D / j)
      return (float)(l * (t = (float)(t - 2.25D / j)) * t + 0.9375D); 
    return (float)(l * (t = (float)(t - 2.625D / j)) * t + 0.984375D);
  }
  
  public static void reset() {
    lastMS = System.currentTimeMillis();
  }
  
  public static boolean hasTimeElapsed(long ms, boolean reset) {
    if (System.currentTimeMillis() - lastMS > ms) {
      if (reset)
        reset(); 
      return true;
    } 
    return false;
  }
  
  public void resett() {
    lastMS = System.currentTimeMillis();
  }
}
