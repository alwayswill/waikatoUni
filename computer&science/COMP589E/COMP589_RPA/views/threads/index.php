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

    <?=
    GridView::widget([
        'dataProvider' => $dataProvider,
//        'filterModel' => $searchModel,
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
            ['class' => 'yii\grid\ActionColumn',
                'template' => '{view} {delete}{start}',
                'buttons' => [
                    'delete' => function ($url, $model) {
                        return Html::a('<span class="glyphicon glyphicon-remove"></span>', $url, [
                                'title' => Yii::t('app', 'Stop the process'),
                                "id"=> "stopPro"
                        ]);
                    },
                    'start' => function ($url, $model) {
                        return Html::a('<span class="glyphicon glyphicon-play"></span>', $url, [
                                'title' => Yii::t('app', 'Start the process'), "id"=> "startPro"
                        ]);
                    }
                ]
            ]
        ],
    ]);
    ?>

</div>
<!--<script type="text/javascript">
    $("#startPro").click(function() {
  alert( "Handler for .click() called." );
});
</script>  -->