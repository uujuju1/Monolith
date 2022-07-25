package monolith.content;

import mindustry.gen.*;
import mindustry.type.*;
import monolith.type.*;

public class MonolithUnits {
  public static UnitType test;

  public void load() {
    test = new SubmersibleUnitType("test") {{
      health = 1650;
      speed = 1f;
      range = maxRange = 25f * 8f;
      hitSize = 12f;
      constructor = LegsUnit::create;
    }};
  }
}