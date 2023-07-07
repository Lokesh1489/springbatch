package com.nt.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nt.model.Asset;
import com.nt.model.AssetDTO;

@Component
public class AssetProcessor implements ItemProcessor<AssetDTO, AssetDTO> {

	private String status = "yes";

	@Override
	public AssetDTO process(AssetDTO item) throws Exception {
		System.out.println("AssetProcessor.process()");
		if (item.getApproveStatus().equalsIgnoreCase("no")) {
			
			item.setApproveStatus(status);
		}
		return item;

	}
}
