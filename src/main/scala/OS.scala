object OS extends Enumeration {
  type OS = Value
  val LINUX, MAC_OS_X, WINDOWS, SUN_OS, FREEBSD, UNKNOWN = Value

  def _OS_NAME_ : OS = {
    java.lang.System.getProperty("os.name").toLowerCase() match {
      case "linux" => OS.LINUX
      case "mac os x" => OS.MAC_OS_X
      case other =>
        if (other.contains("windows")) {
          OS.WINDOWS
        } else if (other.contains("sun")) {
          OS.SUN_OS
        } else if (other.contains("freebsd")) {
          OS.FREEBSD
        } else {
          OS.UNKNOWN
        }
    }
  }
}
