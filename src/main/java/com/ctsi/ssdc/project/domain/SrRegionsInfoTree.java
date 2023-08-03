package com.ctsi.ssdc.project.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SrRegionsInfoTree extends SrRegionsInfo{

    private List<SrRegionsInfoTree> children = new ArrayList<>();
}
