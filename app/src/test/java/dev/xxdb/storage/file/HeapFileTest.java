package dev.xxdb.storage.file;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import dev.xxdb.storage.disk.DiskManager;
import dev.xxdb.storage.page.Page;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.*;

public class HeapFileTest {
  @Test()
  public void testAssertionsEnabled() {
    assertThrows(
        AssertionError.class,
        () -> {
          assert false; // make sure assertions are enabled with VM argument: -ea
        });
  }

  private DiskManager diskManager;
  private HeapFile heapFile;

  @BeforeEach
  void setup() {
    diskManager = mock(DiskManager.class);
    heapFile = new HeapFile(diskManager);
  }

  @Nested
  class GetPageTest {
    // Testing strategy
    // + partition on appearance of the page: has the page, doesn't have the page
    // + partition on this: empty file, have some page datas

    // cover this is an empty file, does not have the page
    @Test
    void emptyPage() throws IOException {
      assertFalse(heapFile.getPage(1).isPresent());
    }

    // cover this has some data, but still does not have the page
    @Test
    void hasDataDoesNotHaveThePage() throws IOException {
      Page p1 = heapFile.newPage();
      Page p2 = heapFile.newPage();
      int notExistedPageId = Math.max(p1.getPageId(), p2.getPageId() + 1);
      assertFalse(heapFile.getPage(notExistedPageId).isPresent());
    }

    // cover this has some data, and has the page
    @Test
    void hasDataHasPage() throws IOException {
      Page p1 = heapFile.newPage();
      Page p2 = heapFile.newPage();
      Page p3 = heapFile.newPage();

      // flush p2 to the disk
      doNothing().when(diskManager).writeSync(anyInt(), any());
      heapFile.savePage(p2);

      // read p2 back
      byte[] actualP2Data = p2.getSerializedData();
      byte[] fakeP2ReturnData = Arrays.copyOf(actualP2Data, actualP2Data.length); 
      when(diskManager.read(eq(p2.getPageId() * Page.PAGE_SIZE), eq(Page.PAGE_SIZE))).thenReturn(fakeP2ReturnData);
      Optional<Page> p2Back = heapFile.getPage(p2.getPageId());
      assertTrue(p2Back.isPresent());
      assertEquals(p2, p2Back.get());
    }
  }
}
