package dev.xxdb.storage.disk;

import java.io.IOException;

public class DiskManager {

  /**
   * Read data from the disk
   *
   * @param offset: starting position to read
   * @param size: how many bytes to read
   * @return a bytes array of data
   * @throws IOException if something went wrong
   */
  public byte[] read(int offset, int size) throws IOException {
    throw new RuntimeException("unimplemented");
  }

  /**
   * Write data to the disk
   *
   * @param offset: starting position to write
   * @param data: the buffer to write
   * @throws IOException if something went wrong
   */
  public void write(int offset, byte[] data) throws IOException {
    throw new RuntimeException("unimplemented");
  }

  /**
   * Write data to the disk, force sync immediately
   *
   * @param offset: starting position to write
   * @param data: the buffer to write
   * @throws IOException if something went wrong
   */
  public void writeSync(int offset, byte[] data) throws IOException {
    throw new RuntimeException("unimplemented");
  }

  /** Force the data to sync to the disk */
  public void sync() throws IOException {
    throw new RuntimeException("unimplemented");
  }
}
