<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of RPAController
 *
 * @author lixiaoxin
 */

namespace app\controllers;

use Yii;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\filters\VerbFilter;

class RPAController extends Controller {

    function init() {
        parent::init();
    }

   public function behaviors() {
       return [
           'access' => [
               'class' => AccessControl::className(),
//                'only' => ['logout'],
               'rules' => [
                   [
                       'allow' => true,
                       'roles' => ['@'],
                   ],
               ],
           ],
           'verbs' => [
               'class' => VerbFilter::className(),
               'actions' => [
                   'logout' => ['post'],
               ],
           ],
       ];
   }

}

?>
