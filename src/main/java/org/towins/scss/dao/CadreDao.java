package org.towins.scss.dao;

import org.towins.scss.dto.qo.CadreQo;
import org.towins.scss.dto.vo.CadreVo;

import java.util.List;

public interface CadreDao {
    long queryCount(CadreQo qo);
    List<CadreVo> queryBy(CadreQo qo);
    List<CadreVo> queryForExport(CadreQo qo);
}
