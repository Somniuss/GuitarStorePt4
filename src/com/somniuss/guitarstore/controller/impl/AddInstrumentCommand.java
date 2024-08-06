package com.somniuss.guitarstore.controller.impl;

import com.somniuss.guitarstore.controller.Command;
import com.somniuss.guitarstore.entity.BassGuitar;
import com.somniuss.guitarstore.entity.ElectricGuitar;
import com.somniuss.guitarstore.entity.MusicalInstrument;
import com.somniuss.guitarstore.logic.GuitarstoreLogic;
import com.somniuss.guitarstore.logic.GuitarstoreLogicProvider;

public class AddInstrumentCommand implements Command {
	private final GuitarstoreLogic logic = GuitarstoreLogicProvider.getInstance().getGuitarstoreLogic();

	@Override
	public String execute(String request) {
		String response;
		String[] params = request.split("\n");

		if (params.length < 4) {
			return "Error: Insufficient data provided.";
		}

		try {
			String type = params[0].trim();
			String brand = params[1].trim();
			String model = params[2].trim();
			double price = Double.parseDouble(params[3].trim());

			MusicalInstrument instrument;

			if (type.equalsIgnoreCase("ElectricGuitar")) {
				if (params.length < 6) {
					return "Error: Insufficient data for ElectricGuitar.";
				}
				String bodyShape = params[4].trim();
				String tremoloSystem = params[5].trim();
				instrument = new ElectricGuitar(type, brand, model, price, bodyShape, tremoloSystem);
			} else if (type.equalsIgnoreCase("BassGuitar")) {
				if (params.length < 5) {
					return "Error: Insufficient data for BassGuitar.";
				}
				String electronics = params[4].trim();
				instrument = new BassGuitar(type, brand, model, price, electronics);
			} else {
				instrument = new MusicalInstrument(type, brand, model, price);
			}

			try {
				logic.addInstrument(instrument);
				response = "Instrument added successfully.";
			} catch (Exception e) {
				response = "Error adding instrument: " + e.getMessage();
			}
		} catch (NumberFormatException e) {
			response = "Error: Invalid number format.";
		} catch (Exception e) {
			response = "Error processing request: " + e.getMessage();
		}

		return response;
	}
}
