package com.somniuss.guitarstore.dao.impl;

import com.somniuss.guitarstore.dao.DaoException;
import com.somniuss.guitarstore.dao.GuitarStoreDao;
import com.somniuss.guitarstore.entity.BassGuitar;
import com.somniuss.guitarstore.entity.ElectricGuitar;
import com.somniuss.guitarstore.entity.MusicalInstrument;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileGuitarStoreDao implements GuitarStoreDao {

	private static final String FILE_NAME = "instruments.txt";

	@Override
	public void addInstrument(MusicalInstrument instrument) throws DaoException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
			writer.write(convertInstrumentToString(instrument));
			writer.newLine();
		} catch (IOException e) {
			throw new DaoException("Error adding instrument to file", e);
		}
	}

	@Override
	public MusicalInstrument findInstrumentById(int id) throws DaoException {
		try {
			return getAllInstruments().stream().filter(instrument -> instrument.getId() == id).findFirst().orElse(null);
		} catch (Exception e) {
			throw new DaoException("Error finding instrument by ID", e);
		}
	}

	@Override
	public List<MusicalInstrument> findInstrumentsByType(String type) throws DaoException {
		try {
			return getAllInstruments().stream().filter(instrument -> instrument.getType().equalsIgnoreCase(type))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new DaoException("Error finding instruments by type", e);
		}
	}

	@Override
	public List<MusicalInstrument> findInstrumentsByBrand(String brand) throws DaoException {
		try {
			return getAllInstruments().stream().filter(instrument -> instrument.getBrand().equalsIgnoreCase(brand))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new DaoException("Error finding instruments by brand", e);
		}
	}

	@Override
	public List<MusicalInstrument> sortInstrumentsByPrice(boolean ascending) throws DaoException {
		try {
			return getAllInstruments().stream()
					.sorted(Comparator.comparingDouble(MusicalInstrument::getPrice).reversed().reversed())
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new DaoException("Error sorting instruments by price", e);
		}
	}

	@Override
	public List<MusicalInstrument> filterInstrumentsByPrice(double maxPrice) throws DaoException {
		try {
			return getAllInstruments().stream().filter(instrument -> instrument.getPrice() <= maxPrice)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new DaoException("Error filtering instruments by price", e);
		}
	}

	@Override
	public void updateInstrument(MusicalInstrument instrument) throws DaoException {
		try {
			List<MusicalInstrument> instruments = getAllInstruments();
			int index = instruments.indexOf(findInstrumentById(instrument.getId()));
			if (index >= 0) {
				instruments.set(index, instrument);
				writeAllInstrumentsToFile(instruments);
			}
		} catch (Exception e) {
			throw new DaoException("Error updating instrument", e);
		}
	}

	@Override
	public boolean deleteInstrumentById(int id) throws DaoException {
		try {
			List<MusicalInstrument> instruments = getAllInstruments();
			boolean removed = instruments.removeIf(instrument -> instrument.getId() == id);
			if (removed) {
				writeAllInstrumentsToFile(instruments);
			}
			return removed;
		} catch (Exception e) {
			throw new DaoException("Error deleting instrument by ID", e);
		}
	}

	private List<MusicalInstrument> getAllInstruments() throws DaoException {
		List<MusicalInstrument> instruments = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			while ((line = reader.readLine()) != null) {
				instruments.add(convertStringToInstrument(line));
			}
		} catch (IOException e) {
			throw new DaoException("Error reading instruments from file", e);
		}
		return instruments;
	}

	private void writeAllInstrumentsToFile(List<MusicalInstrument> instruments) throws DaoException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			for (MusicalInstrument instrument : instruments) {
				writer.write(convertInstrumentToString(instrument));
				writer.newLine();
			}
		} catch (IOException e) {
			throw new DaoException("Error writing instruments to file", e);
		}
	}

	private String convertInstrumentToString(MusicalInstrument instrument) {
		String base = instrument.getId() + "," + instrument.getType() + "," + instrument.getBrand() + ","
				+ instrument.getModel() + "," + instrument.getPrice();

		if (instrument instanceof ElectricGuitar) {
			ElectricGuitar eg = (ElectricGuitar) instrument;
			String result = base + "," + eg.getBodyShape() + "," + eg.getTremoloSystem();
			return result;
		} else if (instrument instanceof BassGuitar) {
			BassGuitar bg = (BassGuitar) instrument;
			return base + "," + bg.getElectronics();
		}

		return base;
	}

	private MusicalInstrument convertStringToInstrument(String line) {
		String[] parts = line.split(",");

		if (parts.length < 5) {
			throw new IllegalArgumentException("Invalid data format: " + line);
		}

		int id = Integer.parseInt(parts[0]);
		String type = parts[1];
		String brand = parts[2];
		String model = parts[3];
		double price = Double.parseDouble(parts[4]);

		if (type.equalsIgnoreCase("ElectricGuitar")) {
			if (parts.length < 7) {
				throw new IllegalArgumentException("Invalid data format for ElectricGuitar: " + line);
			}
			return new ElectricGuitar(id, type, brand, model, price, parts[5], parts[6]);
		} else if (type.equalsIgnoreCase("BassGuitar")) {
			if (parts.length < 6) {
				throw new IllegalArgumentException("Invalid data format for BassGuitar: " + line);
			}
			return new BassGuitar(id, type, brand, model, price, parts[5]);
		}

		return new MusicalInstrument(id, type, brand, model, price);
	}
}
