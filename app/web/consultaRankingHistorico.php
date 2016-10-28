<?php 

include 'connect.php';




if (isset($_GET['idCategoria']) && isset($_GET['Ultimo']))
{
	$idCategoria=$_GET['idCategoria'];
//	$sql = "select * from RankingHistorico,categoria where idRankingHistorico='$idRanking'";
	$sql = "select * from RankingHistorico,Categoria where rankinghistorico.Data=(select MAX(Data) from rankinghistorico)  AND RankingHistorico.Categoria_idCategoria=Categoria.idCategoria AND RankingHistorico.Categoria_idCategoria='$idCategoria' ORDER BY HORA DESC";
}
else if (isset($_GET['Ultimo'])) //se setado em 1 pega os dados do ranking mais recente (último registro adicionado)
{
	$ultimo = $_GET['Ultimo'];
	$sql = "select * from RankingHistorico,Categoria where rankinghistorico.Data=(select MAX(Data) from rankinghistorico) AND rankinghistorico.Hora=(select MAX(Hora) from rankinghistorico) AND RankingHistorico.Categoria_idCategoria=Categoria.idCategoria"; 
}
else 
	$sql = "select * from RankingHistorico,categoria";
	


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


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

		mysqli_close($conn);

?>