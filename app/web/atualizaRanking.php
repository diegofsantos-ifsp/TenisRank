<?php 


class AtualizarRanking 
{
	private $idCategoria;
	private $Ultimo;
	public $conn;
	private $ranking;
	
	public function __construct()
	{
		$this->conn = mysqli_connect("localhost","root","","tenis") or die ("Erro" . mysqli_error($this->conn));
	}
	
	public function __destruct()
	{
		mysqli_close($this->conn);
	}
	
	
	public function setIDCategoria($newval)
	{
		$this->idCategoria = $newval;
	}
	
	public function getIDCategoria()
	{
		return $this->idCategoria;
	}
	
	
	public function setUltimo ($newval)
	{
		$this->Ultimo = $newval;
	}
	
	public function getUltimo ()
	{
		return $this->Ultimo;
	}
	
	//retorna o ID da tabela RankingHistorico do último ranking para a categoria selecionada;
	
	public function retornaidUltimoRanking($idCat)
	{
		$result = 0;
		$sql = "select * from RankingHistorico,Categoria where rankinghistorico.Data=(select MAX(Data) from rankinghistorico)  AND RankingHistorico.Categoria_idCategoria=Categoria.idCategoria AND RankingHistorico.Categoria_idCategoria='$idCat' ORDER BY HORA DESC";				
		$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
		$i=0;
		if (mysqli_num_rows($res) > 0)
		{
			
			$row = mysqli_fetch_array($res);
			
			$result=$row["idRankingHistorico"];
			
		//	echo "Resultado: $result";
			
		}
		
		return $result;
	}
	
	public function moveElementosArray($a, $i, $j)
	{
	  $tmp =  $a[$i];
      if ($i > $j)
      {
           for ($k = $i; $k > $j; $k--) {
                $a[$k] = $a[$k-1]; 
           }        
      }
      else
      { 
           for ($k = $i; $k < $j; $k++) {
                $a[$k] = $a[$k+1];
           }
      }
      $a[$j] = $tmp;
      return $a;
		
	}
	
	public function retornaDadosUltimoRanking($idCat)
	{
		$result = array();
		
		$i=0;
		
		$idRanking = $this->retornaidUltimoRanking($idCat);
		if ($idRanking>0)
		{
			
			$sql = "select usuario.idUsuarios,usuario.Nome,usuario.Endereco,usuario.Telefone,usuario.Email,usuario.NomeUsuario,usuario.CadastroValido,tenista.idTenistas,tenista_has_rankinghistorico.Posicao,tenista.Categoria_idCategoria,tenista.EstaNoRanking  from usuario,tenista,tenista_has_rankinghistorico,rankingHistorico where rankingHistorico.idRankingHistorico='$idRanking' AND rankinghistorico.idRankingHistorico=tenista_has_rankinghistorico.RankingHistorico_idRankingHistorico AND tenista_has_rankinghistorico.Tenista_idTenistas=tenista.idTenistas AND usuario.idUsuarios=tenista.Usuarios_idUsuarios order by tenista_has_rankinghistorico.Posicao";
			$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
			if (mysqli_num_rows($res) > 0)
			{
					
				while ($row = mysqli_fetch_array($res))
				{
					$result[$i]=$row["idTenistas"];
					$i++;	
				}
			}
		}
		
		return $result;
	}
	
	public function lerDesafiosForaDoRanking($idCat)
	{
		$result = array();
		
		$i=0;
		
		
				
			$sql =  "select * from Desafio where Jogado=1 AND EstaNoRanking=0 AND Categoria_idCategoria=$idCat ORDER BY DATA ASC";
			$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
			if (mysqli_num_rows($res) > 0)
			{
					
				while ($row = mysqli_fetch_array($res))
				{
					$result[$i]=$row;
				
				}
			}
		
		//var_dump($result);
		return $result;
	}
	
	public function retornaCategorias()
	{
		$result = array();
		
		$i=0;
		
		$idRanking = $this->retornaidUltimoRanking($idCat);
		if ($idRanking>0)
		{
				
			$sql = "select * from Categoria";
			$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
			if (mysqli_num_rows($res) > 0)
			{
					
				while ($row = mysqli_fetch_array($res))
				{
					$result[$i]=$row["idCategoria"];
					$i++;
				}
			}
		}
		
		return $result;
	}
	
	
	public function getAllTenistas($idCat)
	{
		$result = array();
		$i=0;
		$sql = "select * from usuario,tenista where usuario.idUsuarios=Tenista.Usuarios_idUsuarios AND tenista.Categoria_idCategoria=$idCat";
		$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
		if (mysqli_num_rows($res) > 0)
		{
				
			while ($row = mysqli_fetch_array($res))
			{
				$result[$i]=$row;
				$i++;
			}
		}
		
		
		return $result;
	}
	
	public function getAllTenistasCadastroValidoENaoRanking($idCat)
	{
		$result = array();
		$i=0;
		$sql = "select * from usuario,tenista where tenista.Usuarios_idUsuarios=usuario.idUsuarios AND tenista.Categoria_idCategoria=$idCat AND Usuario.CadastroValido=1 AND Tenista.EstaNoRanking=0";
		$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
		if (mysqli_num_rows($res) > 0)
		{
	
			while ($row = mysqli_fetch_array($res))
			{
				$result[$i]=$row;
				$i++;
			}
		}
	
	
		return $result;
	}
	
	public function getTenistaCadastroValido($ten,$arrayTenista)
	{
		if (!empty($arrayTenista))
		{
			foreach ($arrayTenista as $tenista)
			{
				if ($tenista["idTenistas"]==$ten)
				{
					if ($tenista["CadastroValido"]==1)
						return true;
					else
						return false;
					
				}
			}
		}
		return false;
	}
	
	
	public function podeColocarTenistaRanking($ten, $arrayTenista)
	{
		if (!empty($arrayTenista))
		{
			foreach ($arrayTenista as $tenista)
			{
				if ($tenista["idTenistas"]==$ten)
				{
					if ($tenista["CadastroValido"]==1 && $tenista["EstaNoRanking"]==0)
						return true;
					else
						return false;
				}
			}
		}
		return false;
	}
	
	public function getPosTenista($pos)
	{
		$i=0;
		foreach ($this->ranking as $tenista)
		{
			if ($tenista == $pos)
				return $i;
		
		
			$i++;
				
		}
		return $i;
	}
	
	
	
	public function atualizaRankingComDesafios($idCat)
	{

		$desafios = $this->lerDesafiosForaDoRanking($idCat);
		//$this->ranking=$this->retornaDadosUltimoRanking(1);
		
		//muda a posição dos jogadores no ranking atual
		$this->ranking=$this->retornaDadosUltimoRanking($idCat);
		

		
		
		if (!empty($desafios))
		{
			foreach ($desafios as $desafio)
			{
				$idTenistaDesafiador = $desafio["TenistaDesafiador_idTenistas"];
				$idTenistaDesafiado = $desafio["TenistaDesafiado"];
				$desafiadorPontosSeGanhar = $desafio["DesafiadorPontosSeGanhar"];
				
				$desafiadoPontosSeGanhar = $desafio["DesafiadoPontosSeGanhar"];
				$desafiadorPontosSePerder = $desafio["DesafiadorPontosSePerder"];
				$desafiadoPontosSePerder = $desafio["DesafiadoPontosSePerder"];
				//$desafiadoPontosSePerder = 3;
				$ganhador = $desafio["Ganhador"];
				
				
				//var_dump($this->ranking);
				//echo "\n\n";
				
				if (!empty($this->ranking))
				{
					//pega a posicao do tenista desafiador e desafiado
					$posDesafiador=$this->getPosTenista($idTenistaDesafiador);
					$posDesafiado=$this->getPosTenista($idTenistaDesafiado);
					
					
					//echo "Tenista Desafiador: $posDesafiador e Tenista Desafiado $posDesafiado\n";
					
					//desafiador ganhou
					if ($ganhador == 1)
					{
						//desafiado perdedor
						
						//se o desafiador pegará o lugar do desafiado
						if ($posDesafiador-$posDesafiado == $desafiadorPontosSeGanhar)
						{
						
							//desafiador ganhador
							$posicao = $posDesafiador-$desafiadorPontosSeGanhar;
							if ($posDesafiador-$desafiadorPontosSeGanhar<0)
								$posicao = 0;
							
								$this->ranking = $this->moveElementosArray($this->ranking, $posDesafiador, $posicao);
							
							
								//desafiado perdedor
						if ($desafiadoPontosSePerder>1)
						{
						$posDesafiado = $this->getPosTenista($idTenistaDesafiado);
							
						$posicao = $posDesafiado+($desafiadoPontosSePerder-1);
						if ($posDesafiado+($desafiadoPontosSePerder-1)> (count($this->ranking)-1))
							$posicao = count($this->ranking)-1;
								
								
								
							$this->ranking = $this->moveElementosArray($this->ranking, $posDesafiado, $posicao);
						
						}
							
						
						}
						else //se o desafiador não pegará o lugar do desafiado, primeiro descer posição do desafiado
						{
							$posDesafiado = $this->getPosTenista($idTenistaDesafiado);
								
							$posicao = $posDesafiado+($desafiadoPontosSePerder);
							if ($posDesafiado+($desafiadoPontosSePerder)> (count($this->ranking)-1))
								$posicao = count($this->ranking)-1;
							
							
							
								$this->ranking = $this->moveElementosArray($this->ranking, $posDesafiado, $posicao);
							
							
									
								//depois subir posição do desafiador ganhador
								$posicao = $posDesafiador-$desafiadorPontosSeGanhar;
								if ($posDesafiador-$desafiadorPontosSeGanhar<0)
									$posicao = 0;
							
									$this->ranking = $this->moveElementosArray($this->ranking, $posDesafiador, $posicao);
						}
						
						
					}
					else if ($ganhador == 2)
					{
						$posicao = $posDesafiado-$desafiadoPontosSeGanhar;
						if ($posicao<0)
							$posicao=0;
						
						$this->ranking = $this->moveElementosArray($this->ranking, $posDesafiado, $posicao);
						
						$posicao = $posDesafiador+$desafiadorPontosSePerder;
						if ($posicao> (count($this->ranking)-1))
							$posicao = count($this->ranking)-1;
						
						$this->ranking = $this->moveElementosArray($this->ranking, $posDesafiador, $posicao);
					}
					
					
				}
				
			}
			
			
		}
	
		
		
		
		
		
		//se já existia um ranking anterior
		if (!empty($this->ranking))
		{
		$data = date('Y-m-d');
		//$data="2016-10-21";
		$hora = date('H:i:s');
			
		//echo "Data: $data e Hora $hora";
			
		$sql = "insert into RankingHistorico (Data, Hora, Categoria_idCategoria) values (\"$data\",\"$hora\",$idCat)";

		$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));

		
		if ($res)
		{
			
			$idRankingHistorico = mysqli_insert_id($this->conn);

			
		
			
			$tenistas = $this->getAllTenistas($idCat);
			
			
			//verifica se o ranking inserido é o primeiro do mês
			$sql = "select COUNT(*) from RankingHistorico where YEAR(DATA)=YEAR(CURRENT_DATE) AND MONTH(DATA)=MONTH(CURRENT_DATE)";
			$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
			$contagemRanking=0;
			if (mysqli_num_rows($res) > 0)
			{
			
				$i=0;
				while ($row = mysqli_fetch_array($res))
				{
					$contagemRanking=$row;
					$i++;
				}
			}
	
			

			
			//se não existia ranking criado anteriormente (o primeiro desse mês foi criado nesse momento no código acima)
			if ($contagemRanking==1)
			{
				
				//consulta a regra no banco que diz quantos pontos o tenista perderá caso não jogue nenhuma partida no mês				
				$sql = "select * from Regra";
				$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));

				$regra_pontosCasoNaoJogue = 0;
				
				if (mysqli_num_rows($res) > 0)
				{
						
					$i=0;
					$row = mysqli_fetch_array($res);
					
					$regra_pontosCasoNaoJogue = $row["QtdPosCaiCasoNaoDesafieMes"];
						
					
				}	
				
				//	verifica quais tenistas não jogaram no último mês... quem não jogou terá que perder pontos
				foreach ($tenistas as $ten)
				{
					if ($ten["CadastroValido"]==1 AND $ten["EstaNoRanking"]==1)
					{
						$id = $ten["idTenistas"];
						$sql = "select COUNT(*) from Desafio where (TenistaDesafiador_idTenistas=$id OR TenistaDesafiado=$id) AND JOGADO=1 AND YEAR(DATA)=YEAR(CURRENT_DATE) AND MONTH(DATA)=MONTH(CURRENT_DATE-INTERVAL 1 MONTH)";
						$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
						
						$contagemJogosTenista=0;
						if (mysqli_num_rows($res) > 0)
						{
								
							$i=0;
							while ($row = mysqli_fetch_array($res))
							{
								$contagemJogosTenista=$row;
								$i++;
							}
						}
						
						//tenista não jogou... atualizar o ranking com a perda dos pontos
						if ($contagemJogosTenista==0)
						{
							$posTenistaRanking = $this->getPosTenista($id);
							$novaPosicao = $posTenistaRanking+$regra_pontosCasoNaoJogue;
							if ($novaPosicao==count($this->ranking)-1)
								$novaPosicao=count($this->ranking)-1;
							$this->ranking = $this->moveElementosArray($this->ranking, $posTenistaRanking, $novaPosicao);
							
						}
						
						
					}
				}
			
			}
			
			$posRank=1;
			//armazenar o novo ranking no banco
			foreach ($this->ranking as $tenista)
			{
				//verificar se o tenista possui cadastro válido antes de inserir no novo ranking
				
				if ($this->getTenistaCadastroValido($tenista, $tenistas))
				{
				$sql = "insert into Tenista_has_RankingHistorico (Tenista_idTenistas, RankingHistorico_idRankingHistorico, Posicao) values ($tenista,$idRankingHistorico,$posRank)";
				$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
				$sql = "update Tenista SET PosicaoAtualRanking=$posRank where idTenistas=$tenista";
				$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
				
				
				$posRank++;
				}
				else //atualiza a posicao do rank atual para 0 e não insere no novo ranking
				{
					$sql = "update Tenista SET PosicaoAtualRanking=0 where idTenistas=$tenista";
					$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
					
				}
				
				
			}
			
			//atualiza todas as tabelas desafio onde EstaNoRanking=0 (pois agora já estarão no ranking)
			$sql = "update Desafio SET EstaNoRanking = 1 where Jogado=1 AND EstaNoRanking=0 AND Categoria_idCategoria=$idCat";
			$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
				
			
			
			//procurar por todos os usuários que não estão no ranking (com cadastro valido) e cadastrar no final do ranking
			$tenistasValidos=$this->getAllTenistasCadastroValidoENaoRanking($idCat);
			
			//var_dump($tenistasValidos);
			
			if (!empty($tenistasValidos))
			{
								
				foreach ($tenistasValidos as $ten)
				{
					
					$idTenista = $ten["idTenistas"];
					$sql = "insert into Tenista_has_RankingHistorico (Tenista_idTenistas, RankingHistorico_idRankingHistorico, Posicao) values ($idTenista,$idRankingHistorico,$posRank)";
					$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
					$sql = "update Tenista SET PosicaoAtualRanking=$posRank where idTenistas=$idTenista";
					$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
					
					//atualiza o tenista com o EstaNoRanking=1
					if ($res)
					{
						$sql = "update Tenista SET EstaNoRanking=1 where idTenistas=$idTenista";
						$res2 = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
					}
					
					$posRank++;

					
				}
			}
			
			
			
			
		
			
		}
		else //está vazio o ranking, colocar os usuários com cadastro válido que ainda não estão no ranking
		{
			$tenistas=$this->getAllTenistasCadastroValidoENaoRanking($idCat);
			
			if (!empty($tenistas))
			{
				$data = date('Y-m-d');
			//	$data="2016-10-21";
				$hora = date('H:i:s');
					
				//echo "Data: $data e Hora $hora";
					
				$sql = "insert into RankingHistorico (Data, Hora, Categoria_idCategoria) values (\"$data\",\"$hora\",$idCat)";
				
				$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
				
				
				if ($res)
				{
					$idRankingHistorico = mysqli_insert_id($this->conn);
					$posRank=1;
					
					foreach($tenistas as $ten)
					{
					$idTenistas=$ten["idTenistas"];
					$sql = "insert into Tenista_has_RankingHistorico (Tenista_idTenistas, RankingHistorico_idRankingHistorico, Posicao) values ($idTenistas,$idRankingHistorico,$posRank)";
					$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
					$sql = "update Tenista SET PosicaoAtualRanking=$posRank where idTenistas=$idTenistas";
					$res = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
					
					if ($res)
					{
					$sql = "update Tenista SET EstaNoRanking=1 where idTenistas=$idTenistas";
					$res2 = mysqli_query($this->conn,$sql) or die ("Erro na consulta" .mysqli_error($this->conn));
					}	
					
					
					$posRank++;
					}	
				}
			}
		}
		
		

		
//		var_dump($this->ranking);
		
	//	$resultado=$this->moveElementosArray($resultado, 3, 7);
		//var_dump($resultado);
		}
	}
	
	
	public function fecharBanco()
	{
		mysqli_close($this->conn);
	}
	
}






$rank = new AtualizarRanking;

//$res = array();

//$rank->conecta();

if (isset($_POST["idCat"]))
{
	$idCat = $_POST["idCat"];
	$rank->atualizaRankingComDesafios($idCat);
}

else //atualiza o ranking de todas as categorias 
{
	
}



$result = 1;

echo json_encode($result);
//$rank->lerDesafiosForaDoRanking(1);

//$rank->fecharBanco();

//for ($x=0; $x<array_count($res); $x++)
//	echo $res[$x];


//var_dump($res);
/*
if (isset($_GET['idCategoria']) && isset($_GET['Ultimo']))
{
	$idCategoria=$_GET['idCategoria'];
//	$sql = "select * from RankingHistorico,categoria where idRankingHistorico='$idRanking'";
	$sql = "select * from RankingHistorico,Categoria where rankinghistorico.Data=(select MAX(Data) from rankinghistorico) AND rankinghistorico.Hora=(select MAX(Hora) from rankinghistorico) AND RankingHistorico.Categoria_idCategoria=Categoria.idCategoria AND RankingHistorico.Categoria_idCategoria='$idCategoria'";
}
else if (isset($_GET['Ultimo'])) //se setado em 1 pega os dados do ranking mais recente (último registro adicionado)
{
	$ultimo = $_GET['Ultimo'];
	$sql = "select * from RankingHistorico,Categoria where rankinghistorico.Data=(select MAX(Data) from rankinghistorico) AND rankinghistorico.Hora=(select MAX(Hora) from rankinghistorico) AND RankingHistorico.Categoria_idCategoria=Categoria.idCategoria"; 
}
else 
	$sql = "select * from RankingHistorico,categoria";
	*/




/*
if (mysqli_num_rows($res) > 0)
{

	$result["ranking"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$ranking = array();
		$ranking["idRankingHistorico"] = $row["idRankingHistorico"];
		$ranking["Data"] = $row["Data"];
		$ranking["Hora"] = $row["Hora"];
		$ranking["Categoria_idCategoria"] = $row["Categoria_idCategoria"];
		$ranking["Nome_Categoria"]=$row["Nome"];
		
		array_push($result["ranking"],$ranking);
		//array_push($result,array('id'=>$row[0],'nome'=>$row[1],'endereco'=>$row[2],'telefone'=>$row[3],'email'=>$row[4],'usuario'=>$row[5],'senha'=>$row[6]));
	}
	
		$result["result"]=1;
				
}
else
{
	$result["result"]=0;
}

echo json_encode($result);
*/
	//	mysqli_close($conn);

?>