package com.uniSystemAPI.domain.produto.DTOs.Get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// #region
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexHeader {
    int indexHeader;
    String tituloCategoria;

    // public  IndexHeader(IndexHeader indexHeader) {
    //     this.indexHeader = indexHeader.indexHeader;
    // }

}
