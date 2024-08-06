package com.somniuss.guitarstore.logic;

import com.somniuss.guitarstore.entity.MusicalInstrument;
import com.somniuss.guitarstore.logic.LogicException;

import java.util.List;

public interface GuitarstoreLogic {

	void addInstrument(MusicalInstrument instrument) throws LogicException;

	MusicalInstrument findInstrumentById(int id) throws LogicException;

	List<MusicalInstrument> findInstrumentsByType(String type) throws LogicException;

	List<MusicalInstrument> findInstrumentsByBrand(String brand) throws LogicException;

	List<MusicalInstrument> sortInstrumentsByPrice(boolean ascending) throws LogicException;

	List<MusicalInstrument> filterInstrumentsByPrice(double maxPrice) throws LogicException;

	void updateInstrument(MusicalInstrument instrument) throws LogicException;

	boolean deleteInstrumentById(int id) throws LogicException;
}
