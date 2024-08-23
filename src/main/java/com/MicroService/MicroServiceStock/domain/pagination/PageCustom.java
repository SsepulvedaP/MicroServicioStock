package com.MicroService.MicroServiceStock.domain.pagination;

import java.util.List;

public class PageCustom <T>{
    private List<T> content;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private boolean ascending;
    private boolean empty;

    public PageCustom(List<T> content, int totalElements, int totalPages, int currentPage, boolean ascending) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.ascending = ascending;
        this.empty = content.isEmpty();
    }
    public PageCustom(){

    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
