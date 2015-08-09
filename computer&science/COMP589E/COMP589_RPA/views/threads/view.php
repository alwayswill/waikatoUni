<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model app\models\RPAThreads */

$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Rpathreads', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="rpathreads-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'pid',
            'memory',
            'time:ntext',
            'CPU',
            'command:ntext',
            'IP',
        ],
    ]) ?>

</div>
