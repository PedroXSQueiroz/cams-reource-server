package br.com.pedroxsqueiroz.camsresourceserver.daos;

import org.springframework.stereotype.Repository;

import br.com.pedroxsqueiroz.camsresourceserver.models.CamModel;

@Repository
public interface CamDao extends NodeDao<CamModel> {

}
