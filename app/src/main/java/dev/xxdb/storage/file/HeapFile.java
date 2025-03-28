package dev.xxdb.storage.file;

import dev.xxdb.storage.page.Page;
import java.util.Optional;

public class HeapFile {
  /** Get a Page data given pageId */
  public Optional<Page> getPage(int pageId) {
    throw new RuntimeException("unimplemented");
  }

  /** Allocate a new Page */
  public Page newPage() {
    throw new RuntimeException("unimplemented");
  }
}
