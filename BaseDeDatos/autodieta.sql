-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-02-2019 a las 17:13:13
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `autodieta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dieta`
--

CREATE TABLE `dieta` (
  `IdUsuario` int(11) NOT NULL,
  `IdReceta` int(11) NOT NULL,
  `IdHora` int(11) NOT NULL,
  `Dia` varchar(10) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `dieta`
--

INSERT INTO `dieta` (`IdUsuario`, `IdReceta`, `IdHora`, `Dia`) VALUES
(2, 1, 1, 'Jueves'),
(2, 1, 1, 'Sábado'),
(2, 2, 1, 'Lunes'),
(2, 2, 1, 'Miércoles'),
(2, 2, 3, 'Domingo'),
(2, 2, 3, 'Sábado'),
(2, 2, 4, 'Domingo'),
(2, 3, 2, 'Lunes'),
(2, 4, 3, 'Lunes'),
(2, 4, 3, 'Miércoles'),
(2, 4, 3, 'Viernes'),
(2, 5, 4, 'Lunes'),
(2, 6, 1, 'Domingo'),
(2, 6, 1, 'Martes'),
(2, 6, 1, 'Viernes'),
(2, 7, 2, 'Martes'),
(2, 8, 3, 'Jueves'),
(2, 8, 3, 'Martes'),
(2, 9, 4, 'Martes'),
(2, 10, 2, 'Miércoles'),
(2, 11, 4, 'Miércoles'),
(2, 12, 2, 'Jueves'),
(2, 13, 2, 'Domingo'),
(2, 13, 4, 'Jueves'),
(2, 14, 2, 'Viernes'),
(2, 15, 4, 'Viernes'),
(2, 16, 2, 'Sábado'),
(2, 17, 4, 'Sábado'),
(3, 1, 1, 'Jueves'),
(3, 1, 1, 'Sábado'),
(3, 2, 1, 'Lunes'),
(3, 2, 1, 'Miércoles'),
(3, 2, 3, 'Domingo'),
(3, 2, 3, 'Sábado'),
(3, 2, 4, 'Domingo'),
(3, 3, 2, 'Lunes'),
(3, 4, 3, 'Lunes'),
(3, 4, 3, 'Miércoles'),
(3, 4, 3, 'Viernes'),
(3, 5, 4, 'Lunes'),
(3, 6, 1, 'Domingo'),
(3, 6, 1, 'Martes'),
(3, 6, 1, 'Viernes'),
(3, 7, 2, 'Martes'),
(3, 8, 3, 'Jueves'),
(3, 8, 3, 'Martes'),
(3, 9, 4, 'Martes'),
(3, 10, 2, 'Miércoles'),
(3, 11, 4, 'Miércoles'),
(3, 12, 2, 'Jueves'),
(3, 13, 2, 'Domingo'),
(3, 13, 4, 'Jueves'),
(3, 14, 2, 'Viernes'),
(3, 15, 4, 'Viernes'),
(3, 16, 2, 'Sábado'),
(3, 17, 4, 'Sábado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hora`
--

CREATE TABLE `hora` (
  `Id` int(11) NOT NULL,
  `Hora` varchar(20) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `hora`
--

INSERT INTO `hora` (`Id`, `Hora`) VALUES
(1, 'Desayuno'),
(2, 'Comida'),
(3, 'Merienda'),
(4, 'Cena');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horareceta`
--

CREATE TABLE `horareceta` (
  `IdHora` int(11) NOT NULL,
  `IdReceta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `horareceta`
--

INSERT INTO `horareceta` (`IdHora`, `IdReceta`) VALUES
(1, 1),
(1, 2),
(1, 6),
(1, 18),
(2, 3),
(2, 5),
(2, 7),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(2, 16),
(2, 18),
(3, 1),
(3, 4),
(3, 6),
(3, 8),
(3, 18),
(4, 5),
(4, 9),
(4, 11),
(4, 13),
(4, 15),
(4, 17),
(4, 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `privilegio`
--

CREATE TABLE `privilegio` (
  `Id` int(11) NOT NULL,
  `Privilegio` varchar(20) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `privilegio`
--

INSERT INTO `privilegio` (`Id`, `Privilegio`) VALUES
(1, 'admin'),
(2, 'usuario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta`
--

CREATE TABLE `receta` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `Ingredientes` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `Descripcion` text COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `receta`
--

INSERT INTO `receta` (`Id`, `Nombre`, `Ingredientes`, `Descripcion`) VALUES
(1, 'Cereales integrales con leche', 'Cereales integrales y leche', 'Cuenco de cereales integrales bajos o sin azúcares añadidos con leche'),
(2, 'Bocadillo de jamón y queso', 'Pan, jamón cocido y queso', 'Bocadillo de jamón cocido y queso'),
(3, 'Ensalada de patata', 'Patata, atún, aceite y huevos', 'Patata cocida con una lata de atún, unos huevos duros y un poco de aceite'),
(4, 'Tortitas de maíz con leche', 'Tortitas de maíz y un vaso de leche', 'Tortitas de maíz (4 raciones aprox.) acompañadas de un vaso de leche sola o con cacao en polvo natural'),
(5, 'Puré de calabacín', 'Calabacín', 'Calabacín hecho puré'),
(6, 'Pan con aceite', 'Pan y aceite', 'Tostadas o pan normal con aceite y, opcionalmente, acompañadas con un pellizco de sal'),
(7, 'Pollo con arroz', 'Pollo, arroz y aceite', 'Pollo cortado en pedazos con arroz y no demasiado aceite, lo justo'),
(8, 'Frutos secos con yogur', 'Almendras, pistachos, anacardos y yogur', 'Surtido de frutos secos naturales acompañados con un yogur natural sin azúcares añadidos'),
(9, 'Pescado con verduras', 'Pescado a elegir y verduras varias', 'Pescado a elegir acompañado de verduras'),
(10, 'Macarrones', 'Macarrones y aceite', 'Macarrones con un poco de aceite'),
(11, 'Pescado a la plancha', 'Pescado a elegir', 'Pescado a elegir cocinado a la plancha'),
(12, 'Lentejas', 'Lentejas', 'Lentejas calentitas'),
(13, 'Filete con huevo frito', 'Filete a elegir y huevo frito', 'Filete a elegir entre cerdo, ternera, pavo o pollo acompañado con uno o dos huevos fritos'),
(14, 'Arroz con huevo frito', 'Arroz, aceite y huevo frito', 'Arroz con un poco de aceite y huevo frito'),
(15, 'Hamburguesa', 'Hamburguesa a elegir', 'Hamburguesa de cerdo, ternera, pavo o pollo'),
(16, 'Espirales con atún', 'Espirales, aceite y atún', 'Pasta en espirales con un poco de aceite y una lata de atún'),
(17, 'Filete con verduras', 'Filete a elegir y verduras', 'Filete a elegir entre cerdo, ternera, pavo o pollo acompañado con verduras'),
(18, 'Libre', '', 'Comida libre controlada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Id` int(11) NOT NULL,
  `IdPrivilegio` int(11) NOT NULL,
  `Nombre` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `Contrasena` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `ExpiraDieta` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Id`, `IdPrivilegio`, `Nombre`, `Contrasena`, `ExpiraDieta`) VALUES
(1, 1, 'admin', '37bd45d638c2d11c49c641d2e9c4f49f406caf3ee282743e0c800aa1ed68e2ee', '0000-00-00'),
(2, 2, 'usuario', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '2019-03-15'),
(3, 2, 'usuarioED', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '2019-02-26');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `dieta`
--
ALTER TABLE `dieta`
  ADD PRIMARY KEY (`IdUsuario`,`IdReceta`,`IdHora`,`Dia`),
  ADD KEY `IdReceta` (`IdReceta`),
  ADD KEY `IdHora` (`IdHora`);

--
-- Indices de la tabla `hora`
--
ALTER TABLE `hora`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `horareceta`
--
ALTER TABLE `horareceta`
  ADD PRIMARY KEY (`IdHora`,`IdReceta`),
  ADD KEY `IdReceta` (`IdReceta`);

--
-- Indices de la tabla `privilegio`
--
ALTER TABLE `privilegio`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `receta`
--
ALTER TABLE `receta`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `IdPrivilegio` (`IdPrivilegio`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dieta`
--
ALTER TABLE `dieta`
  ADD CONSTRAINT `dieta_ibfk_1` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`),
  ADD CONSTRAINT `dieta_ibfk_2` FOREIGN KEY (`IdReceta`) REFERENCES `receta` (`Id`),
  ADD CONSTRAINT `dieta_ibfk_3` FOREIGN KEY (`IdHora`) REFERENCES `hora` (`Id`);

--
-- Filtros para la tabla `horareceta`
--
ALTER TABLE `horareceta`
  ADD CONSTRAINT `horareceta_ibfk_1` FOREIGN KEY (`IdHora`) REFERENCES `hora` (`Id`),
  ADD CONSTRAINT `horareceta_ibfk_2` FOREIGN KEY (`IdReceta`) REFERENCES `receta` (`Id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`IdPrivilegio`) REFERENCES `privilegio` (`Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
