package io.biologeek.expenses.services;

public interface Merger<T> {

	public T merge(T stored, T toMerge);
}
