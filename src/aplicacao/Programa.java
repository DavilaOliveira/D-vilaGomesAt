package aplicacao;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Imovel;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Imovel imovel;

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Scanner entrada = new Scanner(System.in);
		int opcao;

		while (true) {
			System.out.println("Digite 1: Para inserir um imóvel");
			System.out.println("Digite 2: Para listar imóveis");
			System.out.println("Digite 3: Para alterar um imóvel");
			System.out.println("Digite 4: Para remover um imóvel");
			System.out.println("Digite 5: Para sair do programa");

			while (true) {
				try {
					opcao = Integer.parseInt(entrada.nextLine());
					break;
				} catch (NumberFormatException e) {
					System.out.println("Tente novamente!");
				}
			}

			switch (opcao) {

			case 1:

				System.out.println("Digite o endereço: ");
				String endereco = entrada.nextLine();
				System.out.println("Digite o número: ");
				Integer numero = Integer.parseInt(entrada.nextLine());
				imovel = new Imovel(null, endereco, numero);
				entityManager.getTransaction().begin();
				entityManager.persist(imovel);
				entityManager.getTransaction().commit();
				break;

			case 2:
				String jpql = "SELECT p FROM Imovel p";
				List<Imovel> imoveis = entityManager.createQuery(jpql, Imovel.class).getResultList();
				if (imoveis.isEmpty() != true) {
					for (int i = 0; i < imoveis.size(); i++) {
						System.out.println("Imóvel " + (i + 1) + ":");
						System.out.println(imoveis.get(i) + "\n");
					}
					System.out.println();
				} else
					System.out.println("Não há ninguém no Banco de Dados. Você precisa cadastrar.");

				break;

			case 3:
				System.out.println("Digite o ID: ");
				int id1;
				while (true) {
					try {
						id1 = Integer.parseInt(entrada.nextLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("Lembre-se que o ID trata-se de um número inteiro.");
					}
				}

				Imovel ImovelFound = entityManager.find(Imovel.class, id1);

				if (ImovelFound != null) {
					System.out.println("Digite o novo endereço: ");
					String endereco1 = entrada.nextLine();
					System.out.println("Digite o novo número: ");
					int numero1 = Integer.parseInt(entrada.nextLine());
					ImovelFound.setEndereco(endereco1);
					ImovelFound.setNumero(numero1);
					entityManager.getTransaction().begin();
					entityManager.persist(ImovelFound);
					entityManager.getTransaction().commit();
					System.out.println("Alteração concluída com sucesso!");

				} else {
					System.out.println(
							"Não é possível a alteração requisitada pois o ID digitado não existe no Banco de Dados.");
				}

				break;
			case 4:
				System.out.println("Digite o ID referente ao imóvel que deseja remover: ");
				int id2;
				while (true) {
					try {
						id2 = Integer.parseInt(entrada.nextLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("Lembre-se que o ID trata-se de um número inteiro.");
					}
				}
				Imovel imovelFound2 = entityManager.find(Imovel.class, id2);
				if (imovelFound2 == null) {
					System.out.println("Não é possível remover o ID desejado, pois não existe.");
				} else {
					System.out.println(imovelFound2);
				}
				entityManager.getTransaction().begin();
				entityManager.remove(imovelFound2);
				entityManager.getTransaction().commit();
				break;

			case 5:
				System.out.println("Fechando programa...Tchauuu!");
				entityManager.close();
				entityManagerFactory.close();
				System.exit(0);
				break;

			default:
				System.out.println("Certifique-se que o número que você digitou condiz com as opcões disponíveis!");
				break;
			}

		}
	}

}