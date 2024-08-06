package com.somniuss.guitarstore.logic.impl;

import com.somniuss.guitarstore.dao.DaoProvider;
import com.somniuss.guitarstore.dao.GuitarStoreDao;
import com.somniuss.guitarstore.dao.DaoException;
import com.somniuss.guitarstore.entity.MusicalInstrument;
import com.somniuss.guitarstore.logic.GuitarstoreLogic;
import com.somniuss.guitarstore.logic.LogicException;

import java.util.List;

public class GuitarstoreLogicImpl implements GuitarstoreLogic {

	private final GuitarStoreDao guitarStoreDao = DaoProvider.getInstance().getGuitarStoreDao();

	@Override
	public void addInstrument(MusicalInstrument instrument) throws LogicException {
		try {
			guitarStoreDao.addInstrument(instrument);
		} catch (DaoException e) {
			throw new LogicException("Error adding instrument", e);
		}
	}

	@Override
	public MusicalInstrument findInstrumentById(int id) throws LogicException {
		try {
			return guitarStoreDao.findInstrumentById(id);
		} catch (DaoException e) {
			throw new LogicException("Error finding instrument by ID", e);
		}
	}

	@Override
	public List<MusicalInstrument> findInstrumentsByType(String type) throws LogicException {
		try {
			return guitarStoreDao.findInstrumentsByType(type);
		} catch (DaoException e) {
			throw new LogicException("Error finding instruments by type", e);
		}
	}

	@Override
	public List<MusicalInstrument> findInstrumentsByBrand(String brand) throws LogicException {
		try {
			return guitarStoreDao.findInstrumentsByBrand(brand);
		} catch (DaoException e) {
			throw new LogicException("Error finding instruments by brand", e);
		}
	}

	@Override
	public List<MusicalInstrument> sortInstrumentsByPrice(boolean ascending) throws LogicException {
		try {
			return guitarStoreDao.sortInstrumentsByPrice(ascending);
		} catch (DaoException e) {
			throw new LogicException("Error sorting instruments by price", e);
		}
	}

	@Override
	public List<MusicalInstrument> filterInstrumentsByPrice(double maxPrice) throws LogicException {
		try {
			return guitarStoreDao.filterInstrumentsByPrice(maxPrice);
		} catch (DaoException e) {
			throw new LogicException("Error filtering instruments by price", e);
		}
	}

	@Override
	public void updateInstrument(MusicalInstrument instrument) throws LogicException {
		try {
			guitarStoreDao.updateInstrument(instrument);
		} catch (DaoException e) {
			throw new LogicException("Error updating instrument", e);
		}
	}

	@Override
	public boolean deleteInstrumentById(int id) throws LogicException {
		try {
			return guitarStoreDao.deleteInstrumentById(id);
		} catch (DaoException e) {
			throw new LogicException("Error deleting instrument by ID", e);
		}
	}
}
