package com.ts.postmaster.dto;

import lombok.Data;

import java.util.List;

/**
 * @author toyewole
 */
@Data
public class DataTableResp<T> {

    boolean hasNext;
    List<T> data;
}
