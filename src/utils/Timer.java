package utils;

public class Timer {
  private long m_start = 0;
  private long m_stop = 0;
  private boolean m_fstarted = false;

  // ----------------------------------------------------------------------
  public void start() {
    m_fstarted = true;
    m_start = System.currentTimeMillis();
  }

  // ----------------------------------------------------------------------
  public void stop() {
    if ( m_fstarted ) {
      m_stop = System.currentTimeMillis();
      m_fstarted = false;
    }
  }

  // ----------------------------------------------------------------------
  public long getElapsedTimeMS() {
    stop();
    long res = m_stop - m_start;
    return res;
  }
  // ----------------------------------------------------------------------
}
