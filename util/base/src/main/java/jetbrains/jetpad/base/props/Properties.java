package jetbrains.jetpad.base.props;

public class Properties {
  private static PropertyProvider ourPropertyProvider = new SystemPropertyProvider();


  public static void setProvider(PropertyProvider provider) {
    ourPropertyProvider = provider;
  }

  public static String get(String key) {
    return ourPropertyProvider.get(key);
  }
}