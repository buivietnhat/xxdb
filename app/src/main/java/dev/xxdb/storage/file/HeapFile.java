package dev.xxdb.storage.file;

import dev.xxdb.storage.disk.DiskManager;
import dev.xxdb.storage.page.Page;
import dev.xxdb.storage.page.SlottedPage;
import dev.xxdb.storage.page.SlottedPageRepository;
import java.io.IOException;
import java.util.Optional;

/** Manage all page data's in/out of the DB */
public class HeapFile implements SlottedPageRepository {

  private final DiskManager diskManager;
  private int nextAvailablePageId = 0;

  // Abstraction function: AF(diskManager, nextAvailablePageId) = a HeapFile data
  // structure that manage all Page data in/out for the DB
  //
  // Rep invariant: true
  //
  // Safety from exposure: all fields are private and final, not exposed to
  // outside

  /** Construct the HeapFile with a DiskManager which knows how to read/write data to the disk */
  public HeapFile(final DiskManager diskManager) {
    this.diskManager = diskManager;
  }

  @Override
  public Optional<SlottedPage> getPage(final int pageId) throws IOException {
    if (pageId >= nextAvailablePageId) {
      return Optional.empty();
    }

    int pageOffset = pageId * Page.PAGE_SIZE;
    byte[] pageData = diskManager.read(pageOffset, Page.PAGE_SIZE);
    return Optional.of(new SlottedPage(pageData));
  }

  @Override
  public void updatePage(final SlottedPage page) throws IOException {
    int pageOffset = page.getPageId() * Page.PAGE_SIZE;
    diskManager.write(pageOffset, page.getSerializedData());
  }

  @Override
  public SlottedPage newPage() {
    SlottedPage page = new SlottedPage(nextAvailablePageId);
    nextAvailablePageId += 1;
    return page;
  }

  @Override
  public void savePage(final SlottedPage page) throws IOException {
    int pageOffset = page.getPageId() * Page.PAGE_SIZE;
    diskManager.writeSync(pageOffset, page.getSerializedData());
  }
}
