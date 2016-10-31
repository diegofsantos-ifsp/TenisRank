<?php 

include 'connect.php';

function moveElementosArray($a, $i, $j)
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


if (isset($_POST['idRanking']) && isset($_POST['idTenistas']) && isset($_POST['novaPos']) && isset($_POST["idCategoria"]))
{
	
//	$sql = "select * from RankingHistorico,categoria where idRankingHistorico='$idRanking'";
	

	
//atualiza o ranking com todos os desafios primeiro
include 'atualizaRanking.php';

//depois atualiza a posição do Tenista enviado para a nova posição desejada
$idCategoria = $_POST['idCategoria'];
$idRanking = $_POST['idRanking'];
$idTenista = $_POST['idTenistas'];
$novaPos = $_POST['novaPos'];


//pega o ID do último Ranking
$sql = "select * from RankingHistorico,Categoria where rankinghistorico.Data=(select MAX(Data) from rankinghistorico)  AND RankingHistorico.Categoria_idCategoria=Categoria.idCategoria AND RankingHistorico.Categoria_idCategoria='$idCategoria' ORDER BY HORA DESC";
$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));
if (mysqli_num_rows($res)>0)
{
	$row=mysqli_fetch_array($res);
	$idRanking = $row["idRankingHistorico"];
}

//pega todos os tenista do último ranking
$sql = "select usuario.idUsuarios,usuario.Nome,usuario.Endereco,usuario.Telefone,usuario.Email,usuario.NomeUsuario,usuario.CadastroValido,tenista.idTenistas,tenista_has_rankinghistorico.Posicao,tenista.Categoria_idCategoria,tenista.EstaNoRanking  from usuario,tenista,tenista_has_rankinghistorico,rankingHistorico where rankingHistorico.idRankingHistorico='$idRanking' AND rankinghistorico.idRankingHistorico=tenista_has_rankinghistorico.RankingHistorico_idRankingHistorico AND tenista_has_rankinghistorico.Tenista_idTenistas=tenista.idTenistas AND usuario.idUsuarios=tenista.Usuarios_idUsuarios order by tenista_has_rankinghistorico.Posicao";
$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$ranking=array();



	$i=0;
	while ($row = mysqli_fetch_array($res))
	{
		
		$ranking[$i]=$row["idTenistas"];
		$i++;
	}

}

$data = date('Y-m-d');
//	$data="2016-10-21";
$hora = date('H:i:s');
	
//echo "Data: $data e Hora $hora";
	
$sql = "insert into RankingHistorico (Data, Hora, Categoria_idCategoria) values (\"$data\",\"$hora\",$idCategoria)";

$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if ($res)
{
	$novoidRankingHistorico = mysqli_insert_id($conn);
	$posRank=1;
	
	$novaPos=$novaPos-1;
	
	//atualizar o novo ranking
	if ($novaPos<0)
		$novaPos=0;
	
	if ($novaPos>count($ranking)-1)
		$novaPos=count($ranking)-1;
	
		
	
	if (!empty($ranking))
	{
		$posAtual=-1;
		$i=0;
		//procura pela posição atual do tenista
		foreach ($ranking as $tenista)
		{
			if ($idTenista==$tenista)
			{
				$posAtual =$i;
				break;
			}
			$i++;
		}
		
		if ($posAtual>=0)
		{
		
		//atualiza o ranking com a nova posição
		$ranking = moveElementosArray($ranking, $posAtual, $novaPos);
		
		$posRank=1;
		foreach ($ranking as $tenista)
			{
			//	armazena o novo ranking
			$sql = "insert into Tenista_has_RankingHistorico (Tenista_idTenistas, RankingHistorico_idRankingHistorico, Posicao) values ($tenista,$novoidRankingHistorico,$posRank)";
			$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));
			$sql = "update Tenista SET PosicaoAtualRanking=$posRank where idTenistas=$tenista";
			$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));
			$posRank++;
		 	}
		}
	}

	
	
}

		$result = array();
		$result["result"]=1;
	echo json_encode($result);
}
		mysqli_close($conn);

?>