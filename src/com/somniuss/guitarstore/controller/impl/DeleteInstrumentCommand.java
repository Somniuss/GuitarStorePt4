package com.somniuss.guitarstore.controller.impl;

import com.somniuss.guitarstore.controller.Command;
import com.somniuss.guitarstore.logic.GuitarstoreLogic;
import com.somniuss.guitarstore.logic.GuitarstoreLogicProvider;

public class DeleteInstrumentCommand implements Command {
	private final GuitarstoreLogic logic = GuitarstoreLogicProvider.getInstance().getGuitarstoreLogic();

	@Override
	public String execute(String request) {
		String response;

		String[] params = request.split("\n");

		if (params.length < 1) {
			return "Error: Insufficient data provided.";
		}

		try {
			int id = Integer.parseInt(params[0].trim());

			boolean result = logic.deleteInstrumentById(id);
			response = result ? "Instrument deleted successfully." : "Instrument not found.";
		} catch (NumberFormatException e) {
			response = "Error: Invalid ID format.";
		} catch (Exception e) {
			response = "Error processing request: " + e.getMessage();
		}

		return response;
	}
}
