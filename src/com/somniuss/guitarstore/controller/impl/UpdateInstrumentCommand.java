package com.somniuss.guitarstore.controller.impl;

import com.somniuss.guitarstore.controller.Command;
import com.somniuss.guitarstore.entity.BassGuitar;
import com.somniuss.guitarstore.entity.ElectricGuitar;
import com.somniuss.guitarstore.entity.MusicalInstrument;
import com.somniuss.guitarstore.logic.GuitarstoreLogic;
import com.somniuss.guitarstore.logic.GuitarstoreLogicProvider;

public class UpdateInstrumentCommand implements Command {
	private final GuitarstoreLogic logic = GuitarstoreLogicProvider.getInstance().getGuitarstoreLogic();

	@Override
	public String execute(String request) {
		String response;
		String[] params = request.split("\n");

		if (params.length < 6) {
			return "Error: Invalid request format.";
		}

		try {
			int id = Integer.parseInt(params[0].split("=")[1].trim());
			String type = params[1].split("=")[1].trim();
			String brand = params[2].split("=")[1].trim();
			String model = params[3].split("=")[1].trim();
			double price = Double.parseDouble(params[4].split("=")[1].trim());

			MusicalInstrument instrument;

			if (type.equalsIgnoreCase("ElectricGuitar")) {
				if (params.length < 7) {
					return "Error: Insufficient data for ElectricGuitar.";
				}
				String bodyShape = params[5].split("=")[1].trim();
				String tremoloSystem = params[6].split("=")[1].trim();
				instrument = new ElectricGuitar(id, type, brand, model, price, bodyShape, tremoloSystem);
			} else if (type.equalsIgnoreCase("BassGuitar")) {
				if (params.length < 6) {
					return "Error: Insufficient data for BassGuitar.";
				}
				String electronics = params[5].split("=")[1].trim();
				instrument = new BassGuitar(id, type, brand, model, price, electronics);
			} else {
				instrument = new MusicalInstrument(id, type, brand, model, price);
			}

			logic.updateInstrument(instrument);
			response = "Instrument updated successfully.";
		} catch (NumberFormatException e) {
			response = "Error: Invalid number format.";
		} catch (Exception e) {
			response = "Error updating instrument: " + e.getMessage();
		}

		return response;
	}
}
