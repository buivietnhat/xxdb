package dev.xxdb.storage.page;

import java.io.IOException;
import java.util.Optional;

public interface SlottedPageRepository {
  /** Get a Page data given pageId */
  Optional<SlottedPage> getPage(final int pageId) throws IOException;

  /** Allocate a new Page */
  SlottedPage newPage();

  /** Update new Page data */
  void updatePage(final SlottedPage page) throws IOException;

  /** Save the Page back to the disk */
  void savePage(final SlottedPage page) throws IOException;
}
