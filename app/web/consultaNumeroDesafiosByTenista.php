<?php 

include 'connect.php';


if (isset($_POST['idTenistas']) && isset($_POST['idCategoria']))
{
	$idTenistas = $_POST['idTenistas'];
	$idCategoria = $_POST['idCategoria'];
	
	$sql = "select COUNT(*) from Desafio where TenistaDesafiador_idTenistas='$idTenistas' AND Jogado=1 AND Categoria_idCategoria=$idCategoria AND YEAR(DATA)=YEAR(CURRENT_DATE) AND MONTH(DATA)=MONTH(CURRENT_DATE)";
	
//	else if ($PORJOGAR && getdadostenistas)
//	$sql = "select * from Desafio where (TenistaDesafiado='$idTenistas' OR TenistasDesafiador_idTenistas='$idTenistas') AND Jogado=0 AND AceitoDesafiado=1 AND AceitoDesafiador=1";
	//	$sql = "";



	
$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


$result = array();

$qtdDesafiosFeitos = 0;


if (mysqli_num_rows($res) > 0)
{


	$row = mysqli_fetch_array($res);
		
	$qtdDesafiosFeitos = $row["COUNT(*)"];
	
	
	//echo "Qtd Desafios: $qtdDesafiosFeitos\n\n";

	$result["result"]=$qtdDesafiosFeitos; 

}	

	

else
	$result["result"]=-1; //pode desafiar


echo json_encode($result);
}
		mysqli_close($conn);

?>