package com.somniuss.guitarstore.dao;

import com.somniuss.guitarstore.entity.MusicalInstrument;

import java.util.List;

public interface GuitarStoreDao {
	void addInstrument(MusicalInstrument instrument) throws DaoException;

	MusicalInstrument findInstrumentById(int id) throws DaoException;

	List<MusicalInstrument> findInstrumentsByType(String type) throws DaoException;

	List<MusicalInstrument> findInstrumentsByBrand(String brand) throws DaoException;

	List<MusicalInstrument> sortInstrumentsByPrice(boolean ascending) throws DaoException;

	List<MusicalInstrument> filterInstrumentsByPrice(double maxPrice) throws DaoException;

	void updateInstrument(MusicalInstrument instrument) throws DaoException;

	boolean deleteInstrumentById(int id) throws DaoException;
}
