<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel app\models\RPAThreadsSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Threads monitor';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="rpathreads-index">

    <h1><?= Html::encode($this->title) ?></h1>
    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>


    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'pid',
            [
                'attribute' => 'memory',
                'format' => 'text',
                'value' => function ($model) {
                    return '' . $model->memory.'K';
                },
            ],
            'time:ntext',
            [
                'attribute' => 'CPU',
                'format' => 'text',
                'value' => function ($model) {
                    return '' . $model->CPU.'%';
                },
            ],
            'command:ntext',
            'IP',
            'status' ,
            ['class' => 'yii\grid\ActionColumn',
                'template' => '{view} {stop}{start}',
                'buttons' => [
                    'stop' => function ($url, $model) {
                        $html =  Html::a('', ['stop', 'id' => $model->id], [
                                'class' => 'glyphicon glyphicon-remove',
                                'data' => [
                                    'confirm' => 'Are you sure you want to stop this process?',
                                    'method' => 'post',
                                ]]);
                        return $html;
                    },
                    'start' => function ($url, $model) {

                        $html =  Html::a('', ['start', 'id' => $model->id], [
                                'class' => 'glyphicon glyphicon-play',
                                'data' => [
                                    'confirm' => 'Are you sure you want to start this process?',
                                    'method' => 'post',
                                ]]);
                        return $html;
                    }
                ]
            ]
        ],
    ]); ?>

</div>
