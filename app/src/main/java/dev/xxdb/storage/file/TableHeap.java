package dev.xxdb.storage.file;

import dev.xxdb.storage.page.Page;
import dev.xxdb.storage.page.SlottedPage;
import dev.xxdb.storage.page.SlottedPageRepository;
import dev.xxdb.storage.tuple.RID;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.storage.tuple.exception.TupleException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/** This class is responsible for managing all tuples in/out for a table */
public class TableHeap {
  private final SlottedPageRepository pageRepository;
  // keep track of list of pageIds of this table
  private final Set<Integer> pageIdList = new HashSet<>();
  private int currentPageId = -1;

  // Abstraction function: AF(pageRepository, pageIdList, currentPageId) = A DB
  // Table consisting a
  // list of Pages, with currentPageId is the Page being in used
  // Rep Invariant: True
  // Safety from rep exposure: all fields are final and private, not exposed to
  // outside

  /**
   * Construct new TableHeap
   *
   * @param pageRepository: knows how to retrieve/add/save Page data's
   */
  public TableHeap(final SlottedPageRepository pageRepository) {
    this.pageRepository = pageRepository;
  }

  /**
   * Get a tuple in this Table
   *
   * @param rid: record id of the tuple
   * @return the tuple if exists
   * @throws TupleException if not found
   */
  public Tuple getTuple(final RID rid) throws TupleException {
    if (!pageIdList.contains(rid.pageId())) {
      throw new TupleException("The tuple doesn't exist");
    }

    try {
      SlottedPage page = pageRepository.getPage(rid.pageId()).get();
      return page.getTuple(rid);
    } catch (IOException e) {
      throw new TupleException(e);
    }
  }

  /**
   * Add a new tuple in this Table
   *
   * @param tuple: tuple data
   * @return record id of the added tuple
   * @throws TupleException if the tuple size > (Page.PAGE_SIZE -
   *                        PAGE_HEADER_SIZE) or cannot add
   *                        the tuple
   */
  public RID addTuple(final Tuple tuple) throws TupleException {
    if (tuple.getSize() >= (Page.PAGE_SIZE - Page.PAGE_HEADER_SIZE)) {
      throw new TupleException("The tuple size is too large, cannot add");
    }

    SlottedPage page;
    if (currentPageId == -1) {
      page = allocateNewPage();
    } else {
      try {
        page = pageRepository.getPage(currentPageId).get();
      } catch (IOException e) {
        throw new TupleException(e);
      }
    }

    if (page.currentAvailableSpace() < tuple.getSize()) {
      page = allocateNewPage();
    }

    RID rid = page.addTuple(tuple);
    try {
      pageRepository.updatePage(page);
    } catch (IOException e) {
      throw new TupleException(e);
    }

    return rid;
  }

  private SlottedPage allocateNewPage() {
    SlottedPage page = pageRepository.newPage();
    currentPageId = page.getPageId();
    pageIdList.add(currentPageId);
    return page;
  }

  /**
   * Delete a tuple out of this Table
   *
   * @param rid: record id of the tuple
   * @return true if the tuple exists and delete successfully
   */
  public boolean deleteTuple(final RID rid) {
    if (!pageIdList.contains(rid.pageId())) {
      return false;
    }

    try {
      SlottedPage page = pageRepository.getPage(rid.pageId()).get();
      boolean deleted = page.deleteTuple(rid);
      if (deleted) {
        pageRepository.updatePage(page);
      }
      return deleted;
    } catch (IOException e) {
      return false;
    }
  }
}
