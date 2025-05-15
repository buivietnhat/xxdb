package dev.xxdb.storage.disk;

import java.io.*;
import java.nio.channels.FileChannel;

public class DiskManager {
  private final RandomAccessFile file;
  private final FileChannel fileChannel;

  public DiskManager(String filePath) throws IOException {
    this.file = new RandomAccessFile(filePath, "rw");
    this.fileChannel = file.getChannel();
  }

  /**
   * Read data from the disk
   *
   * @param offset starting position to read
   * @param size how many bytes to read
   * @return a bytes array of data
   * @throws IOException if something went wrong
   */
  public byte[] read(int offset, int size) throws IOException {
    byte[] buffer = new byte[size];
    file.seek(offset);
    int bytesRead = file.read(buffer);
    if (bytesRead < size) {
      throw new EOFException("Reached end of file before reading all requested bytes.");
    }
    return buffer;
  }

  /**
   * Write data to the disk
   *
   * @param offset starting position to write
   * @param data the buffer to write
   * @throws IOException if something went wrong
   */
  public void write(int offset, byte[] data) throws IOException {
    file.seek(offset);
    file.write(data);
  }

  /**
   * Write data to the disk, force sync immediately
   *
   * @param offset starting position to write
   * @param data the buffer to write
   * @throws IOException if something went wrong
   */
  public void writeSync(int offset, byte[] data) throws IOException {
    write(offset, data);
    sync();
  }

  /** Force the data to sync to the disk */
  public void sync() throws IOException {
    fileChannel.force(true);
  }

  /** Close the file resources */
  public void close() throws IOException {
    file.close();
    fileChannel.close();
  }
}
