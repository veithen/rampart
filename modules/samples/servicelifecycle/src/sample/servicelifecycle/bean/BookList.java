package sample.servicelifecycle.bean;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;

import java.util.HashMap;
import java.util.Iterator;
/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*
*/

public class BookList {

    private HashMap bookTable;
    private String listName;

    public BookList(String listName) {
        this.bookTable = new HashMap();
        this.listName = listName;
    }

    public void addBook(Book book) {
        bookTable.put(book.getIsbn().trim(), book);
    }

    public Book getBook(String isbn) {
        return (Book) bookTable.get(isbn.trim());
    }

    public void removeBook(Book book) {
        bookTable.remove(book.getIsbn());
    }

    public void removeBook(String isbn) {
        bookTable.remove(isbn);
    }

    public HashMap getBookTable() {
        return bookTable;
    }

    public void setBookTable(HashMap bookTable) {
        this.bookTable = bookTable;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public OMElement serialize(OMFactory fac) {
        return null;
    }

    public Book[] getBookList() {
        Book [] books = new Book[bookTable.size()];
        Iterator books_itr = bookTable.values().iterator();
        int count = 0;
        while (books_itr.hasNext()) {
            books[count] = (Book) books_itr.next();
            count ++;
        }
        return books;
    }
}