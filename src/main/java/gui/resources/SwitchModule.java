package gui.resources;

/**
 * Created by razor on 10.06.2017.
 */
public class SwitchModule {
    public static int getMethodNumber(int indexGroup, int indexMethod) {
        switch (indexGroup) {
            case 0: // методы линейной алгебры
                // EpsTextField.setText("0.0001");
                switch (indexMethod) {
                    case 0:// Решение СЛАУ методом Гаусса
                        return (1);
                    case 1:// Метод простой итерации
                        return (2);
                    case 2:// Метод Гаусса-Зейделя
                        return (3);
                    case 3:// Нахождение определителя
                        return (4);
                    case 4:// Нахождение обратной матрицы
                        return (5);
                    default:
                        break;
                }
            case 1:// Методы решения нелинейных уровнений
                switch (indexMethod) {
                    case 0:// Метод простой итерации
                        return (7);
                    case 1:// Метод Ньютона
                        return (8);
                    default:
                }
                break;
            case 2:// Нахождение СЗ и СВ
                switch (indexMethod) {
                    case 0:// Метод Крылова
                        return (9);
                    case 1:
                        break;
                    default:
                        break;
                }
                break;
            case 3:// Интерполирование функции
                switch (indexMethod) {
                    case 0:// Лагранжа
                        return (10);
                    default:
                        break;
                }
                break;
            case 4:// Численное интегрирование
                switch (indexMethod) {
                    case 0:// Метод трапеций
                        return (11);
                    case 1:// Метод парабол
                        return (12);
                    default:
                        break;
                }
                break;
            case 5:
                return (13);
            default:
                return 1;
        }
        return 1;
    }
}
