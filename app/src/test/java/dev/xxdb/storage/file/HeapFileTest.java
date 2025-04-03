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

import dev.xxdb.storage.page.SlottedPage;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

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
      SlottedPage p1 = heapFile.newPage();
      SlottedPage p2 = heapFile.newPage();
      SlottedPage p3 = heapFile.newPage();

      byte[] actualP2Data = p2.getSerializedData();
      byte[] fakeP2ReturnData = Arrays.copyOf(actualP2Data, actualP2Data.length);

      // flush p2 to the disk
      ArgumentCaptor<byte[]> dataCaptor = ArgumentCaptor.forClass(byte[].class);
      doNothing().when(diskManager).writeSync(anyInt(), dataCaptor.capture());

      heapFile.savePage(p2);
      verify(diskManager).writeSync(eq(p2.getPageId() * Page.PAGE_SIZE), any());
      byte[] savedData = dataCaptor.getValue();
      // Assert that the saved data matches p2's data
      assertArrayEquals(actualP2Data, savedData, "Saved data should match the actual page data");

      // read p2 back
      when(diskManager.read(eq(p2.getPageId() * Page.PAGE_SIZE), eq(Page.PAGE_SIZE)))
          .thenReturn(fakeP2ReturnData);
      Optional<SlottedPage> p2Back = heapFile.getPage(p2.getPageId());
      assertTrue(p2Back.isPresent());
      assertEquals(p2, p2Back.get());
    }
  }
}
