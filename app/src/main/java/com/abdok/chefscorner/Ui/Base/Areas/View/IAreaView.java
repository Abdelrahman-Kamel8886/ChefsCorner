package com.abdok.chefscorner.Ui.Base.Areas.View;

import com.abdok.chefscorner.Data.Models.AreasNamesResponseDTO;

import java.util.List;

public interface IAreaView {
    void filterData(List<AreasNamesResponseDTO.AreaNameDTO> areas);
}
