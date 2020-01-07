package com.example.zyjulib.interf;

public interface  ICell<T> {
  void cell(T cell);
  interface ICell2<T, B>{
    void cell(T cell, B cell1);
  }
}
