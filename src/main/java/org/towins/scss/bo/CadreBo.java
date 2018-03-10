package org.towins.scss.bo;

import org.forten.dto.Message;
import org.forten.dto.PageInfo;
import org.forten.dto.PagedRo;
import org.forten.utils.system.BeanPropertyUtil;
import org.towins.dao.HibernateDao;
import org.towins.dao.MybatisDao;
import org.towins.scss.dao.CadreDao;
import org.towins.scss.dto.qo.CadreQo;
import org.towins.scss.dto.ro.PagedRoForEasyUI;
import org.towins.scss.dto.vo.CadreVo;
import org.towins.scss.entity.Cadre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Service("cadreBo")
public class CadreBo {
    @Resource
    private HibernateDao dao;
    @Resource
    private MybatisDao mybatisDao;

    @Transactional
    @Valid
    public Message doSave(Cadre cadre){
        try{
            dao.save(cadre);
            return Message.info("干部信息保存成功！");
        }catch(Exception e){
            e.printStackTrace();
            return Message.error("干部信息保存失败！");
        }
    }

    @Transactional
    @Valid
    public Message doUpdate(CadreVo vo){
        try {
            Cadre c = dao.loadById(Cadre.class, vo.getId());
            BeanPropertyUtil.copy(c, vo);
            return Message.error("干部信息修改成功！");
        }catch(Exception e){
            e.printStackTrace();
            return Message.error("干部信息修改失败！");
        }
    }

    @Transactional(readOnly = true)
    public PagedRoForEasyUI<CadreVo> queryBy(CadreQo qo){
        CadreDao dao = getCadreDao();
        long count = dao.queryCount(qo);

        if(count == 0){
            return new PagedRoForEasyUI<>(new PagedRo<>());
        }

        PageInfo page = PageInfo.getInstance(qo.getPage(),qo.getRows(),(int)count);
        qo.setFirst(page.getFirst());

        List<CadreVo> list = dao.queryBy(qo);

        return new PagedRoForEasyUI<>(new PagedRo<>(list,page));
    }

    @Transactional(readOnly = true)
    public List<CadreVo> queryForExport(CadreQo qo) {
        List<CadreVo> list = getCadreDao().queryForExport(qo);
        return list;
    }

    @Transactional
    public void doBatchSave(Cadre... cadres) {
        dao.save(cadres);
    }

    private CadreDao getCadreDao(){
        return mybatisDao.openSession().getMapper(CadreDao.class);
    }
}
