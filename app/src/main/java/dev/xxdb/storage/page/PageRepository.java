package dev.xxdb.storage.page;

import java.io.IOException;
import java.util.Optional;

public interface PageRepository {
  /** Get a Page data given pageId */
  Optional<Page> getPage(final int pageId) throws IOException;

  /** Allocate a new Page */
  Page newPage();

  /** Save the Page back to the disk */
  void savePage(final Page page) throws IOException;
}
